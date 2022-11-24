package pt.isel.tds.ttt.ui

import androidx.compose.runtime.*
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.storage.BoardSerializer
import pt.isel.tds.ttt.storage.FileStorage
import pt.isel.tds.ttt.storage.Storage

class ViewModel {
    val storage: Storage<String, Board> = FileStorage("games",BoardSerializer)
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

    fun play(pos: Position) {
        if (game?.board is BoardRun) {
            game = game?.play(pos,storage)
            if (game?.board !is BoardRun) stopWatch.pause()
        }
        println("Play: ${Thread.currentThread().name}")
    }

}