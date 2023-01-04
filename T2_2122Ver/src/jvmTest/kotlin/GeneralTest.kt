import kotlin.test.*

class GeneralTest {
    @Test
    fun evelDec() {
        val sut = Eval.of(45)
        assertNotNull(sut)
        assertEquals(Eval.of(40), sut.dec())
        assertEquals(0, checkNotNull(Eval.of(0)).dec().quotation)
    }
}