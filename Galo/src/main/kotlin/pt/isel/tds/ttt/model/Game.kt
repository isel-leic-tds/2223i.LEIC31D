package pt.isel.tds.ttt.model

data class Game(
    val id: String,
    val player: Player,
    val board: Board
)

fun createGame(
    id: String,
    player: Player=Player.X,
    board: Board = initialBoard()
): Game {
    TODO()
}

fun Game.play(pos: Position): Game {
    check(board is BoardRun) { "Game over" }
    check(player == board.turn) { "Not your turn" }
    val newBoard = board.play(pos)
    return copy( board = newBoard)
}
