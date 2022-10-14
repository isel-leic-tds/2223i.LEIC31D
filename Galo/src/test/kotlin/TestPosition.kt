import kotlin.test.*
import pt.isel.tds.ttt.model.*

class TestPosition {
    @Test fun `index to position`() {
        val sut = 4.toPosition()
        assertEquals(1,sut.line)
        assertEquals(1,sut.col)
    }
    @Test fun `line & column to index`() {
        val sut = Position(1,1)
        assertEquals(4,sut.index)
    }
}