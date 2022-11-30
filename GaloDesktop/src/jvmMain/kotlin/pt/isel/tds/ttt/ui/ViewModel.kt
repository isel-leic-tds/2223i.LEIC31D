package pt.isel.tds.ttt.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.isel.tds.storage.FileStorage
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.storage.BoardSerializer
import pt.isel.tds.ttt.storage.BoardStorage

class ViewModel(val scope: CoroutineScope) {
    val refreshEnable: Boolean
        get() {
            val g = game ?: return false
            return !onRefresh && g.board is BoardRun && g.player!=g.board.turn
        }

    val storage: BoardStorage = FileStorage("games", BoardSerializer)
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
            }
        }
        openDialog = !openDialog
    }

    private fun updateGame(g: Game) {
        if (g.board !is BoardRun) stopWatch.pause()
        game = g
    }

    fun play(pos: Position) {
        val g = game ?: return
        if (g.board is BoardRun && g.player==g.board.turn)
            updateGame(g.play(pos, storage, scope))
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