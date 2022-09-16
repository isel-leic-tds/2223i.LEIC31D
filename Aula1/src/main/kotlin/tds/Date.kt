package tds

private val lastDays = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

data class Date(val day: Int, val month:Int, val year:Int): Any() {
    val lastDay
        get() = if (month==2 && isLeapYear) 29 else lastDays[month-1]
        /* when(month) {
            1,3,5,7,8,10,12 -> 31
            2 -> if (isLeapYear) 29 else 28
            else -> 30
        }*/
    val isLeapYear
        get() = year%4==0 && year%100!=0 || year%400==0
    init {
        require(year in 1..4000)
        require(month in 1..12)
        require(day in 1..lastDay)
    }
    override fun toString() = "$day-$month-$year"
    /*
    override fun equals(other: Any?) =
        other is Date && day==other.day && month==other.month && year == other.year
    override fun hashCode() = day+month*31+year*32*16
    */
}

tailrec fun Date.addDays(inc: Int): Date = when {
    day + inc <= lastDay -> Date(day+inc, month, year)
    month == 12 -> Date(1,1,year+1).addDays(inc - (lastDay-day)-1)
    else -> Date(1,nextMonth(),year).addDays(inc - (lastDay-day)-1)
}

fun Date.nextMonth() = month % 12 + 1



