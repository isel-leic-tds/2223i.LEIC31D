
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

class Grade private constructor(val value: Int) {
    companion object {
        const val MIN = 1; const val MAX = 20
        private val scale = (MIN..MAX).map { Grade(it) }
        operator fun invoke(value: Int) = scale[ value - MIN ]
    }
    override fun toString() = "$value values"
}

//@Suppress("EqualsOrHashCode")
class Eval private constructor(val quotation: Int) {
    companion object {
        const val DELTA = 5; const val MIN = 0; const val MAX = 100
        val default = Eval(MIN)
        fun of(q: Int) = if (q in MIN..MAX && q % DELTA == 0) Eval(q) else null
    }
    override fun equals(other: Any?) = other is Eval && other.quotation==quotation
    override fun hashCode() = quotation
    fun inc(): Eval = if (quotation==MAX) this else Eval(quotation+DELTA)
}

fun Eval.dec():Eval = Eval.of(quotation-Eval.DELTA) ?: this

fun main() = singleWindowApplication(
    title = "Quotation Demo",
    state = WindowState(width= 280.dp, height= 130.dp),
) { MaterialTheme {
    DemoEvalEdit( Grade(4) ) { partial: Double ->
        println("Partial = ${partial.format(2)}")
    }
} }
fun Double.format(digs: Int) = "%.${digs}f".format(this)

fun Grade.partialValue(eval: Eval): Double = eval.quotation/100.0 * value

@Composable
fun DemoEvalEdit(grade: Grade, onChange: (Double)->Unit) {

}