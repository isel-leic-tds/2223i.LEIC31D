package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.Board
import pt.isel.tds.ttt.model.initialBoard
import pt.isel.tds.ttt.model.play
import pt.isel.tds.ttt.model.toPositionOrNull

class SyntaxError(msg: String): IllegalArgumentException(msg)

fun playAction(args: List<String>, game: Board?): Board {
    checkNotNull(game) { "Game not created." }
    //require(args.isNotEmpty()) { "Missing position" }
    if (args.isEmpty()) throw SyntaxError("Missing position")
    val pos = args.first().toIntOrNull()?.toPositionOrNull()
        ?: throw SyntaxError("Invalid position ${args.first()}")
    //requireNotNull(pos) { "Invalid position ${args.first()}" }
    return game.play(pos)
}