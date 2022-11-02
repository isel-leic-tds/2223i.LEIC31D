package pt.isel.tds.ttt.model

import pt.isel.tds.ttt.model.Player.O
import pt.isel.tds.ttt.model.Player.X
import pt.isel.tds.ttt.storage.FileStorage
import pt.isel.tds.ttt.storage.Storage

data class Game(
    val id: String,
    val player: Player,
    val board: Board
)

fun createGame(
    id: String,
    storage: Storage<String, Board>
): Game {
    val board = storage.read(id)
    if(board != null && board.moves.size <= 1)
        return Game(id, O, board) // already stored in storage
    if(board != null)
        storage.delete(id) // remove existing board before creating a new one
    return Game(id, X, initialBoard().also { storage.create(id, it) })
}

fun Game.play(pos: Position, storage: Storage<String, Board>): Game {
    check(board is BoardRun) { "Game over" }
    check(player == board.turn) { "Not your turn" }
    val newBoard = board.play(pos)
    storage.update(this.id, newBoard)
    return copy( board = newBoard)
}
