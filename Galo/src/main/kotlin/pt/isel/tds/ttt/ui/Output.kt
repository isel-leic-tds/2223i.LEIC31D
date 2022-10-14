package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.*

/*
 O |   | X
---+---+---
   | X |
---+---+---
   |   |
 */

val divLine = "---+".repeat(BOARD_DIM-1)+"---"

fun Player?.toString() = this?.name ?: " "

fun Board.print() {
    Position.values.forEach { pos ->
        print(" ${get(pos).toString()} ")
        if (pos.col== BOARD_DIM-1) {
            println()
            if (pos.line< BOARD_DIM-1) println(divLine)
        }
        else print("|")
    }
    println(
        when (this) {
            is BoardRun -> "Turn: $turn"
            is BoardDraw -> "Draw"
            is BoardWin -> "Winner: $winner"
        }
    )
}

