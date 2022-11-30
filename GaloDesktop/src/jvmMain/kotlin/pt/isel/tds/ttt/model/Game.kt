package pt.isel.tds.ttt.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.isel.tds.ttt.model.Player.O
import pt.isel.tds.ttt.model.Player.X
import pt.isel.tds.ttt.storage.*

data class Game(
    val id: String,
    val player: Player,
    val board: Board
)

suspend fun createGame(
    id: String,
    storage: BoardStorage
): Game {
    val board = storage.read(id)
    if(board != null && board.moves.size <= 1)
        return Game(id, O, board) // already stored in storage
    if(board != null)
        storage.delete(id) // remove existing board before creating a new one
    return Game(id, X, initialBoard().also { storage.create(id, it) })
}

fun Game.play(pos: Position, storage: BoardStorage, scope: CoroutineScope): Game {
    check(board is BoardRun) { "Game over" }
    check(player == board.turn) { "Not your turn" }
    val newBoard = board.play(pos)
    scope.launch {
        storage.update(this@play.id, newBoard)
    }
    return copy( board = newBoard)
}
