package pt.isel.tds.ttt.model

const val BOARD_DIM = 3

enum class Player{ X, O }
fun Player.other() = if (this==Player.X) Player.O else Player.X

class Position private constructor(val line: Int, val col: Int) {
    companion object {
        val values = List(BOARD_DIM* BOARD_DIM) { idx ->
            Position( idx / BOARD_DIM , idx % BOARD_DIM )
        }
    }
}

class Move(val position: Position, val player: Player)

class Board(val moves: List<Move>)