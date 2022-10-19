package pt.isel.tds.ttt.ui

data class CommandLine(val name: String, val params:List<String>)

fun readCommand(): CommandLine {
    while (true) {
        print("> ")
        val words = readln().split(" ").filter { it.isNotEmpty() }
        if (words.isNotEmpty())
            return CommandLine(words.first().uppercase(), words.drop(1))
    }
}