package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.Board
import pt.isel.tds.ttt.model.play
import pt.isel.tds.ttt.model.toPositionOrNull

fun playAction(args: List<String>, game: Board?): Board {
    checkNotNull(game) { "Game not created." }
    require(args.isNotEmpty()) { "Missing position" }
    val pos = args.first().toIntOrNull()?.toPositionOrNull()
    requireNotNull(pos) { "Invalid position ${args.first()}" }
    return game.play(pos)
}