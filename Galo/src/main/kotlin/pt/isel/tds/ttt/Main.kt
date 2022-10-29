package pt.isel.tds.ttt

import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.storage.FileStorage
import pt.isel.tds.ttt.storage.Serializer
import pt.isel.tds.ttt.ui.*
import pt.isel.tds.ttt.ui.fp.*

fun main() {
    var game: Game? = null
    val cmds = getCommands(FileStorage("out", BoardSerializer))
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

object BoardSerializer : Serializer<Board, String> {
    override fun write(obj: Board): String {
        val boardKind = obj::class.simpleName
        return boardKind + System.lineSeparator() + obj
            .moves
            .map { it.key.index.toString() + it.value } // <index><Player>
            .joinToString(System.lineSeparator())
    }

    override fun parse(stream: String): Board {
        val words = stream.split(System.lineSeparator())
        val boardKind = words[0]
        val moves = words
            .drop(1)
            .filter { it.isNotEmpty() }
            .associate {
                require(it.length == 2) { "Each line must have exactly 2 chars with <index><Player>" }
                Move(it[0].digitToInt().toPosition(), Player.valueOf(it[1].toString()))
            }
        val player = if(moves.isEmpty()) Player.O else moves.values.last()
        return when(boardKind) {
            BoardRun::class.simpleName -> BoardRun(moves, player)
            BoardDraw::class.simpleName -> BoardDraw(moves)
            BoardWin::class.simpleName -> BoardWin(moves, player)
            else -> error("There is not that type of board for $boardKind")
        }
    }
}
