import tennis.oop.*
import kotlin.test.*

class ScoreTest {
    @Test  fun `observe initial Score`() {
        val sut = InitialScore
        assertFalse(sut.isGame())
        assertEquals("0 - 0",sut.placard)
        assertEquals("15 - 0",sut.next(Player.A).placard)
    }
    private fun Score.play(winners: String) =
        winners.fold(this){ state, winner -> state.next(Player.valueOf(winner.toString()))}

    @Test fun `Game of A condition`() {
        val sut = InitialScore.play("AAAA")
        assertTrue(sut.isGame())
        assertEquals("Game of A",sut.placard)
        assertFailsWith<IllegalStateException> { sut.next(Player.A) }
        assertTrue(InitialScore.play("AABABA").isGame())
    }
}