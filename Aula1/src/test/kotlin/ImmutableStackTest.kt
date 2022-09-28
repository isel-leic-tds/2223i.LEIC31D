import tds.*
import kotlin.test.*

class ImmutableStackTest {
    @Test fun `Empty Stack operations`() {
        val sut = emptyStack<Int>()
        assertTrue(sut.isEmpty())
        val e1 = assertFailsWith<NoSuchElementException> { sut.top() }
        assertEquals("empty stack", e1.message)
        val e2 = assertFailsWith<NoSuchElementException> { sut.pop() }
        assertEquals("empty stack", e2.message)
        val after = sut.push(10)
        assertFalse(after.isEmpty())
        assertEquals(10,after.top())
    }
    @Test fun `Non empty Stack operations`() {
        val elems = (0..10)
        var sut = emptyStack<Int>()
        elems.forEach{ sut = sut.push(it) }
        assertFalse(sut.isEmpty())
        assertEquals(elems.last(),sut.top())
        elems.reversed().forEach {
            assertEquals(it,sut.top())
            sut = sut.pop()
        }
        assertTrue(sut.isEmpty())
    }
}