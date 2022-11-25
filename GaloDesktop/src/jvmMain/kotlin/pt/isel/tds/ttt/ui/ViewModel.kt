package pt.isel.tds.ttt.ui

import androidx.compose.runtime.*
import pt.isel.tds.storage.FileStorage
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.storage.BoardSerializer
import pt.isel.tds.ttt.storage.BoardStorage

class ViewModel {
    val storage: BoardStorage = FileStorage("games", BoardSerializer)
    var game: Game? by mutableStateOf(null)
        private set
    val stopWatch = StopWatch()
    var openDialog by mutableStateOf(false)
        private set

    fun newGame(name: String? = null) {
        if (name!=null) {
            game = createGame(name,storage)
            stopWatch.reset()
            stopWatch.start()
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
            updateGame( g.play(pos,storage) )
    }

    fun refresh() {
        val g = game ?: return
        val board = storage.read(g.id)
        checkNotNull(board)
        updateGame( g.copy(board = board) )
    }
}