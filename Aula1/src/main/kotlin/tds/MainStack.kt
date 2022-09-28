package tds

fun main() {
    val stk = MutableStack<String>()
    stk.push("ISEL")
    stk.push("LEIC")
    stk.push("TDS")
    println(stk.top())
    while( !stk.isEmpty() ) {
        val elem = stk.pop()
        println(elem)
    }

    var s = Stack<String>()
    s = s.push("ISEL")
    s = s.push("LEIC")
    s = s.push("TDS")
    println( s.top() )
    while ( !s.isEmpty() ) {
        println(s.top())
        s = s.pop()
    }
}