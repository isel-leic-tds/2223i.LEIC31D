package tennis.oop

import Player

/*
enum class Points(val number:Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40); // , GAME(45);
    fun next() = values()[ordinal+1]
}
*/

class Points private constructor(val number: Int) {
    fun next() = values[values.indexOfFirst { it.number==number }+1]
    companion object {
        val values = listOf(0,15,30).map { Points(it) }
        val LOVE = values.first()
        val THIRTY = values.last()
    }
}

interface Score {
    val placard: String
    fun next(winner: Player): Score
    fun isGame() = false
}

private class Forty(val player: Player, val other:Points) :Score {
    override val placard =
        if (player==Player.A) "40 - ${other.number}"
        else "${other.number} - 40"

    override fun next(winner: Player) = when {
        winner == player -> Game(winner)
        other == Points.THIRTY -> Deuce
        else -> Forty(player,other.next())
    }
}

private open class ByPoints(val pointsA: Points, val pointsB: Points): Score {
    override val placard = "${pointsA.number} - ${pointsB.number}"
    override fun next(winner: Player) = when {
        pointsA==Points.THIRTY && winner==Player.A ||
        pointsB==Points.THIRTY && winner==Player.B ->
            Forty(winner,if (winner==Player.A) pointsB else pointsA)
        else ->
            if (winner==Player.A) ByPoints(pointsA.next(),pointsB)
            else ByPoints(pointsA,pointsB.next())
    }
}

private data class Game(val winner: Player) : Score {
    override val placard = "Game of $winner"
    override fun isGame() = true
    override fun next(winner: Player) = throw IllegalStateException()
}

private data class Advantage(val player: Player): Score {
    override val placard = "Advantage of $player"
    override fun next(winner: Player) =
        if (winner==player) Game(winner)
        else Deuce
}

private object Deuce: Score {
    override val placard = "Deuce"
    override fun next(winner: Player) = Advantage(winner)
}

val InitialScore: Score = ByPoints(Points.LOVE,Points.LOVE)
