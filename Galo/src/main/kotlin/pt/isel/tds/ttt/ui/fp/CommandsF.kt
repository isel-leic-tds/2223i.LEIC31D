package pt.isel.tds.ttt.ui.fp

import pt.isel.tds.ttt.BoardSerializer
import pt.isel.tds.ttt.model.Board
import pt.isel.tds.ttt.model.Game
import pt.isel.tds.ttt.model.createGame
import pt.isel.tds.ttt.model.play
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
            TODO()
        }
    ),
    "EXIT" to Command(
        execute = {_,_ -> null},
        show = { println("Bye.") }
    ),
)
