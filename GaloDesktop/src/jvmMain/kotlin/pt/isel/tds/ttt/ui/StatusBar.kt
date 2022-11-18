package pt.isel.tds.ttt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import pt.isel.tds.ttt.model.*

@Composable
fun StatusBar(g: Board?, sw: StopWatch) {
    Row( Modifier
        .background(MaterialTheme.colors.primary)
        .width(gridViewSize),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        val (txt,player) = when(g) {
            is BoardRun -> "Turn:" to g.turn
            is BoardWin -> "Winner:" to g.winner
            is BoardDraw -> "Game tied" to null
            null -> "Start a new game" to null
        }
        val style: TextStyle = MaterialTheme.typography.h5
        val height = with(LocalDensity.current){ style.fontSize.toDp() }
        Row {
            Text(txt, color = MaterialTheme.colors.onPrimary, style = style)
            CellView(player, Modifier.size(height), {} )
        }
        StopWatchView(Modifier.height(height),sw)
    }
}

@Composable
fun StopWatchView(modifier: Modifier, sw: StopWatch) {
    Text(sw.formattedTime,
        modifier = modifier,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onPrimary
    )
}