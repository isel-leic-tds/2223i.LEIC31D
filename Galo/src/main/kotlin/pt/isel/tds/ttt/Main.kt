import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.playAction
import pt.isel.tds.ttt.ui.print
import pt.isel.tds.ttt.ui.readCommand

fun main() {
    var game: Board? = null
    while( true ) {
        try {
            val (name, args) = readCommand()
            when (name) {
                "EXIT" -> break
                "NEW" -> game = initialBoard()
                "GRID" -> {}
                "PLAY" -> game = playAction(args, game)
            }
            game?.print()
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}



