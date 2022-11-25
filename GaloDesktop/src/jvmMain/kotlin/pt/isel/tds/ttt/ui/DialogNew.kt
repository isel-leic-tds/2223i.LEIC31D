package pt.isel.tds.ttt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState

@Composable
fun DialogNewGame(onOk: (String)->Unit, onCancel: ()->Unit) = Dialog(
    onCloseRequest = onCancel,
    title = "New Game",
    state = DialogState(width = 300.dp, height = Dp.Unspecified)
) {
    var name by remember { mutableStateOf("") }
    Column {
        OutlinedTextField(name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button( onClick = onCancel ){ Text("Cancel") }
            Button( onClick = { onOk(name) }){ Text("New") }
        }
    }
}
