import tds.MutableStack
import kotlin.test.*

class MutableStackTest {
    @Test fun `Empty MutableStack operations`() {
        val sut = MutableStack<Int>()
        assertTrue(sut.isEmpty())
        val e1 = assertFailsWith<NoSuchElementException> { sut.top() }
        assertEquals("empty stack", e1.message)
        val e2 = assertFailsWith<NoSuchElementException> { sut.pop() }
        assertEquals("empty stack", e2.message)
        sut.push(10)
        assertFalse(sut.isEmpty())
        assertEquals(10,sut.top())
    }
    @Test fun `Non empty MutableStack operations`() {
        val elems = (0..10).toList()
        val sut = MutableStack<Int>()
        elems.forEach { sut.push(it) }
        assertFalse(sut.isEmpty())
        assertEquals(elems.last(),sut.top())
        elems.reversed().forEach {
            assertEquals(it,sut.pop())
        }
        assertTrue(sut.isEmpty())
    }
}