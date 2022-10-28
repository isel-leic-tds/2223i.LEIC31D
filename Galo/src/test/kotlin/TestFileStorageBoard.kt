import TestFileStorageBoard.BoardSerializer.parse
import TestFileStorageBoard.BoardSerializer.write
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.model.Player.X
import pt.isel.tds.ttt.storage.Serializer
import java.lang.System.lineSeparator
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestFileStorageBoard {
    object BoardSerializer : Serializer<Board, String> {
        override fun write(obj: Board): String {
            val boardKind = obj::class.simpleName
            return boardKind + lineSeparator() + obj
                .moves
                .map { it.key.index.toString() + it.value } // <index><Player>
                .joinToString(lineSeparator())
        }

        override fun parse(stream: String): Board {
            val words = stream.split(lineSeparator())
            val boardKind = words[0]
            val moves = words
                .drop(1)
                .associate {
                    require(it.length == 2) { "Each line must have exactly 2 chars with <index><Player>" }
                    Move(it[0].digitToInt().toPosition(), Player.valueOf(it[1].toString()))
                }
            val player = moves.values.last()
            return when(boardKind) {
                BoardRun::class.simpleName -> BoardRun(moves, player)
                BoardDraw::class.simpleName -> BoardDraw(moves)
                BoardWin::class.simpleName -> BoardWin(moves, player)
                else -> error("There is not that type of board for $boardKind")
            }
        }
    }

    @BeforeTest fun setup() {

    }

    @Test fun `Check we get an equivalent Board from serialize and deserialize`() {
        val board = BoardRun(emptyMap(), X)
            .play(Position(0,0))
            .play(Position(1,1))
        val actual = parse(write(board))
        assertEquals(board.moves, actual.moves)
        assertIs<BoardRun>(actual)
    }
    @Test fun `Check we get an equivalent BoardWin from serialize and deserialize`() {
        val board = BoardRun(emptyMap(), X)
            .play(Position(0,0)).play(Position(1,1))
            .play(Position(0,1)).play(Position(1,2))
            .play(Position(0,2))
        val actual = parse(write(board))
        assertEquals(board.moves, actual.moves)
        assertIs<BoardWin>(actual)
    }
}
