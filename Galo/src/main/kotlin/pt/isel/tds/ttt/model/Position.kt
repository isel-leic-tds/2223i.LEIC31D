package pt.isel.tds.ttt.model

@JvmInline
value class Position private constructor(val index: Int) {
    val line get() = index / BOARD_DIM
    val col get() = index % BOARD_DIM
    val backSlash get() = line==col
    val slash get() = line+col== BOARD_DIM-1
    companion object {
        val values = List(MAX_MOVES) { idx -> Position(idx) }
        operator fun invoke(l: Int, c: Int) = values[l* BOARD_DIM +c]
    }
    override fun toString() = "($line,$col):$index"
}

fun Int.toPosition() = Position.values[this]
fun Int.toPositionOrNull() =
    if (this in Position.values.indices) toPosition() else null