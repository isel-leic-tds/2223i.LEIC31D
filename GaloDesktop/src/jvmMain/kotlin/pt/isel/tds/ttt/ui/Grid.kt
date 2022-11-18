package pt.isel.tds.ttt.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.tds.ttt.model.*

val cellViewSize = 120.dp
val gridLineSize = 4.dp
val gridViewSize = cellViewSize * BOARD_DIM + gridLineSize * (BOARD_DIM-1)

@Composable
fun BoardView(g: Board?, onPlay: (Position)->Unit ){
    Column {
        repeat(BOARD_DIM) { line ->
            if (line > 0) Spacer(Modifier
                .background(Color.DarkGray)
                .height(gridLineSize).width(gridViewSize)
            )
            Row {
                repeat(BOARD_DIM) { col ->
                    val pos = Position(line, col)
                    if (col > 0) Spacer(Modifier
                        .width(gridLineSize).height(cellViewSize)
                        .background(Color.DarkGray)
                    )
                    CellView(g?.get(pos), Modifier.size(cellViewSize)) {
                        onPlay(pos)
                    }
                }
            }
        }
    }
}

@Composable
fun CellView(player: Player?, modifier: Modifier = Modifier, onClick: ()->Unit) {
    val image = when(player) {
        Player.O -> "circle"
        Player.X -> "cross"
        null -> null
    }
    val mod = modifier
        //.background(Color.White)
        //.border(2.dp, color = Color.DarkGray)
    if (image==null) Box(modifier = mod.clickable(onClick = onClick))
    else
        Image(
            painterResource("$image.png"),
            modifier = mod,
            contentDescription = image
        )
}