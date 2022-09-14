import kotlin.test.*
import tds.Date

class DateTest {
    //@Ignore
    @Test
    fun createDate() {
        val d = Date(14,9,2022)
        assertEquals(14,d.day)
        assertEquals(9,d.month)
        assertEquals(2022,d.year)
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

    }
}



