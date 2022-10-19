import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.*

fun main() {
    var game: Board? = null
    val cmds = getCommands()
    while( true ) {
        try {
            val (name, args) = readCommand()
            val cmd = cmds[name] ?: error("Invalid command $name")
            game = cmd.execute(args,game) ?: break
            cmd.show(game)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}



