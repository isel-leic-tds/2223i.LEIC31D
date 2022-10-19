package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.Board
import pt.isel.tds.ttt.model.initialBoard
import pt.isel.tds.ttt.model.play
import pt.isel.tds.ttt.model.toPositionOrNull

abstract class Command {
    abstract fun execute(args: List<String>, g: Board?) :Board?
    open fun show(g: Board?) { g?.print() }
}

object New: Command() {
    override fun execute(args: List<String>, g: Board?) = initialBoard()
}
object Play: Command() {
    override fun execute(args: List<String>, g: Board?) = playAction(args,g)
}
object Grid: Command() {
    override fun execute(args: List<String>, g: Board?) = g
}
object Exit: Command() {
    override fun execute(args: List<String>, g: Board?) = null
}

fun getCommands(): Map<String, Command> = mapOf(
    "NEW" to New,
    "GRID" to Grid,
    "PLAY" to Play,
    "EXIT" to Exit,
)

fun playAction(args: List<String>, game: Board?): Board {
    checkNotNull(game) { "Game not created." }
    require(args.isNotEmpty()) { "Missing position" }
    val pos = args.first().toIntOrNull()?.toPositionOrNull()
    requireNotNull(pos) { "Invalid position ${args.first()}" }
    return game.play(pos)
}