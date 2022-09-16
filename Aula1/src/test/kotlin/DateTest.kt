import kotlin.test.*
import tds.*
import java.lang.IllegalArgumentException

class DateTest {
    //@Ignore
    @Test
    fun createDate() {
        val month = 9
        val d = Date(14,month,2022)
        assertEquals(14,d.day)
        assertEquals(9,d.month)
        assertEquals(2022,d.year)
        assertFailsWith<IllegalArgumentException> {
            assertEquals(40, Date(40, 6, 2024).day)
        }
    }
    @Test
    fun `convert Date to String`(){
        val d = Date(14,9,2022)
        assertEquals("14-9-2022",d.toString())
    }
    @Test
    fun `Get next month`() {
        val d = Date(14,9,2022)
        assertEquals(10,d.nextMonth())
        assertEquals(1,Date(25,12,2022).nextMonth())
    }
    @Test
    fun `Add days to Date`() {
        val d = Date(14,9,2022)
        assertEquals(Date(16,9,2022),d.addDays(2))
        assertEquals(Date(29,10,2022),d.addDays(45))
        assertEquals(Date(1,1,2023),Date(25,12,2022).addDays(7))
    }
}





