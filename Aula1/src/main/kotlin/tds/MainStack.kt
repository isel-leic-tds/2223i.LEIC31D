package tds

fun main() {
    val stk = MutableStack<String>()
    stk.push("ISEL")
    stk.push("LEIC")
    stk.push("TDS")
    show(stk)
    while( !stk.isEmpty() ) {
        val elem = stk.pop()
        println(elem)
    }

    /*
    var s = emptyStack<String>()
    s = s.push("ISEL")
    s = s.push("LEIC")
    s = s.push("TDS")
     */
    var s = stackOf("ISEL","LEIC","TDS")
    show(s)
    while ( !s.isEmpty() ) {
        println(s.top())
        s = s.pop()
    }
}

fun <T> show(stk: IStack<T>) {
    if (!stk.isEmpty())
        println("Top = ${stk.top()}")
}