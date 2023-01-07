import kotlin.test.*

class GeneralTest {
    @Test
    fun evalDec() {
        val sut = Eval.of(45)
        assertNotNull(sut)
        assertEquals(Eval.of(40), sut.dec())
        assertEquals(0, checkNotNull(Eval.of(0)).dec().quotation)
    }
    @Test
    fun partial() {
        assertEquals(1.4,Grade(4).partialValue(Eval.of(35)?: Eval.default))
    }
}