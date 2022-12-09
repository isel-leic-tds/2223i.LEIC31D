package pt.isel.tds.ttt.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.tds.storage.FileStorage
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.storage.BoardSerializer
import pt.isel.tds.ttt.storage.BoardStorage
import kotlin.time.Duration.Companion.seconds
// Momgo imports
import pt.isel.tds.storage.MongoStorage
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*

class ViewModel(private val scope: CoroutineScope) {
    val refreshEnable: Boolean
        get() {
            val g = game ?: return false
            return !onRefresh && g.board is BoardRun && g.player!=g.board.turn
        }

    private val envConnection = System.getenv("MONGO_CONNECTION")
    //private val client = KMongo.createClient(envConnection).coroutine

    private val storage: BoardStorage =
        FileStorage("games", BoardSerializer)
        //MongoStorage("games", client.getDatabase("galo"), BoardSerializer)

    var game: Game? by mutableStateOf(null)
        private set
    val stopWatch = StopWatch()
    var openDialog by mutableStateOf(false)
        private set
    var onRefresh by mutableStateOf(false)
        private set

    fun newGame(name: String? = null) {
        if (name!=null) {
            scope.launch {
                game = createGame(name, storage)
                stopWatch.reset()
                stopWatch.start()
                val g = game ?: return@launch
                if (!isPlayerTurn()) autoRefresh(g)
            }
        }
        openDialog = !openDialog
    }

    private fun updateGame(g: Game) {
        if (g.board !is BoardRun) stopWatch.pause()
        game = g
    }

    private fun isPlayerTurn(board: Board? = null): Boolean {
        val g = game ?: return false
        val brd = board ?: g.board
        return brd is BoardRun && g.player== brd.turn
    }

    fun play(pos: Position) {
        val g = game ?: return
        if (isPlayerTurn()) {
            updateGame(g.play(pos, storage, scope))
            scope.launch {
                autoRefresh(g)
            }
        }
    }

    private suspend fun autoRefresh(g: Game) {
        var board: Board?
        //print("AutoRefresh:")
        do {
            delay(3.seconds)
            board = storage.read(g.id)
            //print('.')
        } while (board is BoardRun && ! isPlayerTurn(board))
        checkNotNull(board)
        updateGame(g.copy(board = board))
        //println("#")
    }

    fun refresh() {
        val g = game ?: return
        if (onRefresh) return
        onRefresh = true
        scope.launch {
            val board = storage.read(g.id)
            checkNotNull(board)
            updateGame(g.copy(board = board))
            onRefresh = false
        }
    }
}