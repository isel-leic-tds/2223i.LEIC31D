package pt.isel.tds.ttt.ui.oop

import pt.isel.tds.ttt.model.Board
import pt.isel.tds.ttt.model.initialBoard
import pt.isel.tds.ttt.model.play
import pt.isel.tds.ttt.model.toPositionOrNull
import pt.isel.tds.ttt.ui.playAction
import pt.isel.tds.ttt.ui.print

abstract class Command {
    abstract fun execute(args: List<String>, g: Board?) :Board?
    open fun show(g: Board?) { g?.print() }
    open val argsSyntax: String = ""
}

object New: Command() {
    override fun execute(args: List<String>, g: Board?) = initialBoard()
}
object Play: Command() {
    override fun execute(args: List<String>, g: Board?) = playAction(args,g)
    override val argsSyntax = "<position>"
}
object Grid: Command() {
    override fun execute(args: List<String>, g: Board?): Board {
        checkNotNull(g) { "Game not created." }
        return g
    }
}

fun getCommands() = mapOf(
    "NEW" to New,
    "GRID" to Grid,
    "PLAY" to Play,
    "EXIT" to object: Command() {
        override fun execute(args: List<String>, g: Board?) = null
        override fun show(g: Board?) { println("Bye.") }
    },
)
