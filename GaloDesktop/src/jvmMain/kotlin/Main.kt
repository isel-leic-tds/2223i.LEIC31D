import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import pt.isel.tds.ttt.model.*

@Composable
@Preview
fun FrameWindowScope.GaloApp( onExit: ()->Unit ) {
    var game by remember { mutableStateOf(initialBoard()) }
    MenuBar {
        Menu("Game") {
            Item("New") { game = initialBoard() }
            Item("Exit", onClick = onExit )
        }
    }
    Column {
        BoardView(game) { pos: Position ->
            if (game is BoardRun) game = game.play(pos)
        }
        //StatusView(game)
    }
}

val cellViewSize = 120.dp
val gridLineSize = 3.dp

@Composable
fun BoardView(g: Board, onPlay: (Position)->Unit ){
    Column(Modifier.background(Color.DarkGray)) {
       repeat(BOARD_DIM) { line ->
           if (line>0) Spacer(Modifier.height(gridLineSize))
           Row{
              repeat(BOARD_DIM) { col ->
                  val pos = Position(line,col)
                  if (col>0) Spacer(Modifier.width(gridLineSize))
                  CellView(g[pos]) { onPlay(pos) }
              }
           }
       }
    }
}

@Composable
fun CellView(player: Player?, onClick: ()->Unit) {
    val image = when(player) {
        Player.O -> "circle"
        Player.X -> "cross"
        null -> null
    }
    val mod = Modifier
        .size(cellViewSize)
        .background(Color.White)
        //.border(2.dp, color = Color.DarkGray)
    if (image==null) Box( modifier = mod.clickable(onClick=onClick) )
    else
        Image(
            painterResource("$image.png"),
            modifier = mod,
            contentDescription = image
        )
}

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
