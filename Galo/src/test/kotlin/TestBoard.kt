import kotlin.test.*
import pt.isel.tds.ttt.model.*

class TestBoard {
    @Test fun `Initial Board`() {
        val sut = initialBoard()
        assertEquals(0,sut.moves.size)
        assertEquals(Player.X,sut.turn)
    }

    private fun Board.plays(vararg idxPos: Int): Board =
        idxPos.fold(this) { board, idx -> board.play(idx.toPosition()) }

    @Test fun `2 plays makes a BoardRun`() {
        val sut = initialBoard().plays(4,1)
        assertEquals(Player.O,sut[1.toPosition()])
        assertEquals(2,sut.moves.size)
        assertTrue(sut is BoardRun)
        assertEquals(Player.X,sut.turn)
    }
    @Test fun `Makes a BoardDraw`() {
        if (BOARD_DIM!=3) return
        val sut = initialBoard().plays(4, 8, 2, 6, 5, 3, 7, 1, 0)
        assertTrue(sut is BoardDraw)
    }
    @Test fun `Makes a BoradWin`() {
        if (BOARD_DIM!=3) return
        val sut = initialBoard().plays(4, 1, 2, 6, 8, 0, 5)
        assertTrue(sut is BoardWin)
        assertEquals(Player.X,sut.winner)
    }
}