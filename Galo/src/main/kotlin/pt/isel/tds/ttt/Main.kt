import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.print

fun main() {
    val board: Board = initialBoard()
        .play(4.toPosition())
        .play(0.toPosition())
        .play(1.toPosition())
        .play(2.toPosition())
        .play(7.toPosition())
    board.print()
}