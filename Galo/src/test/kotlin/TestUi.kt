import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.ui.fp.getCommands
import pt.isel.tds.ttt.ui.print
import pt.isel.tds.ttt.ui.readCommand
import java.io.*
import kotlin.test.*

class TestUi {
    @Test fun `readCommand PLAY position`() {
        val out = redirectInOut("","Play 4") {
            val (name,args) = readCommand()
            assertEquals("PLAY",name)
            assertEquals(listOf("4"),args)
        }
        assertEquals(listOf("> > "),out)
    }
    @Test fun `print Board`() {
        val sut = initialBoard().play(4.toPosition())
        val out = redirectInOut {
            sut.print()
        }
        assertEquals(listOf(
            "   |   |   ",
            "---+---+---",
            "   | X |   ",
            "---+---+---",
            "   |   |   ",
            "Turn: O"
        ),out)
    }
    @Test fun `exit Command`() {
        val sut = getCommands()["EXIT"]
        assertNotNull(sut)
        assertNull(sut.execute(emptyList(),null))
    }
}

fun redirectInput(input: String, fx: ()->Unit) {
    val oldlIn = System.`in`
    val newIn = ByteArrayInputStream(input.toByteArray())
    System.setIn(newIn)
    fx()
    System.setIn(oldlIn)
}

/**
 * Executes the [test] function with the input and output redirected.
 * @param lines Each line that will be read from the input.
 * @param test Code to test that reads from stdin and writes to stdout.
 * @return Lines written in the output.
 */
private fun redirectInOut(vararg lines:String, test: ()->Unit): List<String> {
    val oldInput = System.`in`
    System.setIn(ByteArrayInputStream(lines.joinToString(System.lineSeparator()).toByteArray()))
    val oldOutput = System.out
    val result = ByteArrayOutputStream()
    System.setOut(PrintStream(result))
    test()
    System.setIn(oldInput)
    System.setOut(oldOutput)
    val out = result.toString().split(System.lineSeparator())
    return if (out.size>1 && out.last().isEmpty()) out.dropLast(1) else out
}
