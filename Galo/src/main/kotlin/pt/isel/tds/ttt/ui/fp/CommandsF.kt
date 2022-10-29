package pt.isel.tds.ttt.ui.fp

import pt.isel.tds.ttt.BoardSerializer
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.storage.FileStorage
import pt.isel.tds.ttt.storage.Storage
import pt.isel.tds.ttt.ui.playAction
import pt.isel.tds.ttt.ui.print

class Command(
    val execute: (args: List<String>, g: Game?) -> Game?,
    val show: (g: Game?) -> Unit = { g -> g?.board?.print() },
    val argsSyntax: String = "",
)

fun getCommands(storage: Storage<String, Board>) = mapOf(
    "NEW" to Command(
        execute = { args, _ ->
            createGame(args[0], storage)
        }
    ),
    "GRID" to Command( execute = {_, g ->
        checkNotNull(g){ "Game not created." }
        g
    } ),
    "PLAY" to Command(
        argsSyntax = "<position>",
        execute = { args, g ->
            require(args.size == 1){ "Please provide a single Index position!" }
            require(args[0].length == 1){ "Position should be a single Integer digit!" }
            require(g != null){ "You must start a new game before playing!" }
            val index = args[0].toIntOrNull()
            require(index != null){ "Not a valid integer index!" }
            g.play(index.toPosition(), storage)
        }
    ),
    "REFRESH" to Command(
        argsSyntax = "refresh",
        execute = { _, g ->
            require(g != null){ "You must start a new game before playing!" }
            val b = storage.read(g.id)
            check(b != null)
            g.copy(board = b)
        }
    ),
    "EXIT" to Command(
        execute = {_,_ -> null},
        show = { println("Bye.") }
    ),
)
