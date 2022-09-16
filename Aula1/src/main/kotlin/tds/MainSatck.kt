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
}