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
    val existing = storage.read(id)
    if(existing == null) {
        val board = initialBoard()
        storage.create(id, board)
        return Game(id, X, board)
    }
    if(existing.moves.size <= 1) {
        return Game(id, O, existing)
    }
    storage.delete(id)
    val board = initialBoard()
    storage.create(id, board)
    return Game(id, X, board)
}

fun Game.play(pos: Position, storage: Storage<String, Board>): Game {
    check(board is BoardRun) { "Game over" }
    check(player == board.turn) { "Not your turn" }
    val newBoard = board.play(pos)
    storage.update(this.id, this.board)
    return copy( board = newBoard)
}
