import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import pt.isel.tds.ttt.ui.GaloApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Galo TDS",
        state = WindowState(
            position= WindowPosition(Alignment.Center),
            size = DpSize.Unspecified
        )
    ) {
        MaterialTheme {
            GaloApp( onExit = ::exitApplication )
        }
    }
}
