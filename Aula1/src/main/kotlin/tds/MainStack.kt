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

    var s = stackOf("ISEL","LEIC","TDS")
    //for(e in s) print("$e ")
    s.forEach{ print("$it ") }
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