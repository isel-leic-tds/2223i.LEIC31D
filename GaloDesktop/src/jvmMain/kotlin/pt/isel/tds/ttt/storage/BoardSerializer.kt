package pt.isel.tds.ttt.storage

import pt.isel.tds.ttt.model.*

object BoardSerializer : Serializer<Board, String> {
    override fun write(obj: Board): String {
        val boardKind = obj::class.simpleName
        return boardKind + System.lineSeparator() + obj
            .moves
            .map { it.key.index.toString() + it.value } // <index><Player>
            .joinToString(System.lineSeparator())
    }

    override fun parse(stream: String): Board {
        val words = stream.split(System.lineSeparator())
        val boardKind = words[0]
        val moves = words
            .drop(1)
            .filter { it.isNotEmpty() }
            .associate {
                require(it.length == 2) { "Each line must have exactly 2 chars with <index><Player>" }
                Move(it[0].digitToInt().toPosition(), Player.valueOf(it[1].toString()))
            }
        val player = if(moves.isEmpty()) Player.X else moves.values.last().other()
        return when(boardKind) {
            BoardRun::class.simpleName -> BoardRun(moves, player)
            BoardDraw::class.simpleName -> BoardDraw(moves)
            BoardWin::class.simpleName -> BoardWin(moves, player)
            else -> error("There is not that type of board for $boardKind")
        }
    }
}
