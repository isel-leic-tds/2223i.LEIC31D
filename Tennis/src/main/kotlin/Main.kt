import tennis.*

fun main() {
    var score: Score = InitialScore
    while (true) {
        println(score.placard)
        if (score.isGame()) break
        score = score.next( readWinner() )
    }
    println("Finish")
}

enum class Player{ A, B }

fun readWinner(): Player {
    print("winner A or B ? ")
    val letter = readln().first()
    return if (letter in "aA") Player.A else Player.B
}