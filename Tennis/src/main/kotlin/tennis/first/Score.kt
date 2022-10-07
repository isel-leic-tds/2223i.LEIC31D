package tennis.first

import Player

enum class Points(val number:Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), GAME(45);
    fun next() = values()[ordinal+1]
}

data class Score(val pointsOfA: Points, val pointsOfB: Points) {
    val placard get() = when {
        pointsOfA == pointsOfB && pointsOfA == Points.FORTY -> "Deuce"
        pointsOfA == Points.GAME && pointsOfB == Points.FORTY -> "Advantage A"
        pointsOfB == Points.GAME && pointsOfA == Points.FORTY -> "Advantage B"
        pointsOfA == Points.GAME -> "Game of A"
        pointsOfB == Points.GAME -> "Game of B"
        else -> "${pointsOfA.number} - ${pointsOfB.number}"
    }
    fun next(winner: Player) = when {
        pointsOfA == Points.GAME && pointsOfB == Points.FORTY ->
            if (winner==Player.A) Score(pointsOfA,Points.LOVE)
            else Score(Points.FORTY,Points.FORTY)
        pointsOfB == Points.GAME && pointsOfA == Points.FORTY ->
            if (winner==Player.B) Score(Points.LOVE,pointsOfB)
            else Score(Points.FORTY,Points.FORTY)
        isGame() -> throw IllegalStateException()
        else ->
            if (winner==Player.A) Score(pointsOfA.next(),pointsOfB)
            else Score(pointsOfA,pointsOfB.next())
    }
    fun isGame() =
        pointsOfA==Points.GAME && pointsOfB!=Points.FORTY ||
        pointsOfB==Points.GAME && pointsOfA!=Points.FORTY

}

val InitialScore = Score(Points.LOVE,Points.LOVE)