package pt.isel.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import pt.isel.tds.ttt.model.*

@Composable
@Preview
fun FrameWindowScope.GaloApp(onExit: ()->Unit ) {
    var game: Board? by remember { mutableStateOf(null) }
    val stopWatch = remember{ StopWatch() }
    MenuBar {
        Menu("Game") {
            Item("New") {
                game = initialBoard()
                stopWatch.reset()
                stopWatch.start()
            }
            Item("Exit", onClick = onExit )
        }
    }
    Column {
        BoardView(game) { pos: Position ->
            if (game is BoardRun) {
                game = game?.play(pos)
                if (game !is BoardRun) stopWatch.pause()
            }
            println("Play: ${Thread.currentThread().name}")
        }
        StatusBar(game, stopWatch)
    }
}