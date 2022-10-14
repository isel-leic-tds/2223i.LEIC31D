package pt.isel.tds.ttt.model

const val BOARD_DIM = 3
const val MAX_MOVES = BOARD_DIM* BOARD_DIM

enum class Player{ X, O }
fun Player.other() = if (this==Player.X) Player.O else Player.X

typealias Move = Pair<Position,Player>

typealias Moves = Map<Position,Player>

sealed class Board(val moves: Moves)
class BoardRun(mvs: Moves, val turn: Player): Board(mvs)
class BoardWin(mvs: Moves, val winner: Player): Board(mvs)
class BoardDraw(mvs: Moves): Board(mvs)

fun initialBoard() = BoardRun(emptyMap(),Player.X)

operator fun Board.get(position: Position): Player? = moves[position]

fun Board.play(pos: Position): Board = when(this){
    is BoardWin, is BoardDraw -> error("Game is over")
    is BoardRun -> {
        require(this[pos]==null) { "position taken $pos" }
        val mvs = moves+Move(pos,turn)
        when {
            checkWin(pos) -> BoardWin(mvs, turn)
            mvs.size == MAX_MOVES -> BoardDraw(mvs)
            else -> BoardRun(mvs, turn.other())
        }
    }
}

private fun BoardRun.checkWin(pos: Position): Boolean {
    val places = moves.filter { it.value==turn }.keys + pos
    return places.count { it.col == pos.col } == BOARD_DIM
        || places.count { it.line == pos.line } == BOARD_DIM
        || places.count { it.backSlash } == BOARD_DIM
        || places.count { it.slash } == BOARD_DIM
}
