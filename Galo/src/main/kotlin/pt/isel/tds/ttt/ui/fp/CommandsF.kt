package pt.isel.tds.ttt.ui.fp

import pt.isel.tds.ttt.model.Board
import pt.isel.tds.ttt.model.initialBoard
import pt.isel.tds.ttt.ui.playAction
import pt.isel.tds.ttt.ui.print

class Command(
    val execute: (args: List<String>, g: Board?) -> Board?,
    val show: (g: Board?) -> Unit = { g -> g?.print() },
    val argsSyntax: String = ""
)

val New = Command(
    execute = { _, _ -> initialBoard() }
)

fun getCommands() = mapOf(
    "NEW" to New,
    "GRID" to Command( execute = {_, g ->
        checkNotNull(g){ "Game not created." }
        g
    } ),
    "PLAY" to Command(
        execute =  ::playAction,
        argsSyntax = "<position>"
    ),
    "EXIT" to Command(
        execute = {_,_ -> null},
        show = { println("Bye.") }
    ),
)
