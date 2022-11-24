package pt.isel.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import pt.isel.tds.ttt.model.*

@Composable
@Preview
fun FrameWindowScope.GaloApp(onExit: ()->Unit ) {
    val viewModel = remember { ViewModel() }
    MenuBar {
        Menu("Game") {
            Item("New") { viewModel.newGame() }
            Item("Exit", onClick = onExit )
        }
    }
    if (viewModel.openDialog)
        DialogNewGame(
            onOk = { name -> viewModel.newGame(name) },
            onCancel = { viewModel.newGame() }
        )
    Column {
        BoardView(viewModel.game?.board, onPlay = viewModel::play)
        StatusBar(viewModel.game?.board, viewModel.stopWatch)
    }
}
