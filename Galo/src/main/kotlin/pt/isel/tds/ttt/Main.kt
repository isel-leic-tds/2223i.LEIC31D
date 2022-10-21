import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.*
import pt.isel.tds.ttt.ui.fp.*

fun main() {
    var game: Board? = null
    val cmds = getCommands()
    while( true ) {
        val (name, args) = readCommand()
        val cmd: Command? = cmds[name]
        if (cmd==null) println("Invalid command $name")
        else try {
            game = cmd.execute(args,game)
            cmd.show(game)
            if (game==null) break
        } catch (ex: Exception) {
            println(ex.message)
            if (ex is SyntaxError)
                println("Use: $name ${cmd.argsSyntax}")
        }
    }
}



