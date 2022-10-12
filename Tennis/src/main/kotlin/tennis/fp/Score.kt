package tennis.fp

import Player
import Player.*

enum class Points(val number:Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30); // FORTY(40); // , GAME(45);
    fun next() = values()[ordinal+1]
}

sealed interface Score
private class Forty(val player: Player, val other:Points) :Score
private open class ByPoints(val pointsA: Points, val pointsB: Points): Score
private data class Game(val winner: Player) : Score
private data class Advantage(val player: Player): Score
private object Deuce: Score

val InitialScore: Score = ByPoints(Points.LOVE,Points.LOVE)

val Score.placard: String
    get() = when(this) {
        Deuce -> "Deuce"
        is Advantage -> "Advantage of $player"
        is ByPoints -> "${pointsA.number} - ${pointsB.number}"
        is Forty -> if (player==A) "40 - ${other.number}" else "${other.number} - 40"
        is Game -> "Game of $winner"
    }

fun Score.isGame() = this is Game

private fun ByPoints.pointsOf(p: Player) = if (p==A) pointsA else pointsB

fun Score.next(winner: Player) :Score = when(this) {
    is Game -> error("not possible")
    is Advantage -> if (winner==player) Game(winner) else Deuce
    is ByPoints -> when {
        pointsOf(winner) == Points.THIRTY -> Forty(winner,pointsOf(winner.other()))
        else -> if (winner==A) ByPoints(pointsA.next(),pointsB)
                else ByPoints(pointsA,pointsB.next())
    }
    Deuce -> Advantage(winner)
    is Forty -> when {
        winner == player -> Game(winner)
        other == Points.THIRTY -> Deuce
        else -> Forty(player, other.next())
    }
}
