import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.*

@Composable
@Preview
fun App() {
    log("App")
    var text by remember { mutableStateOf("Hello, World!") }
    //Text(text.value)
    Button(onClick = {
        text += " Compose"
        println(text)
    }) {
        log("Button")
        Text(text)
    }
}

fun log(msg: String) = println("$msg: ${Thread.currentThread().name}")

fun main() {
    log("main")
    application(exitProcessOnExit = false) {
        log("app")
        Window(onCloseRequest = ::exitApplication) {
            MenuBar {
                Menu("File") {
                    Item("exit") { println("exit") }
                }
            }
            MaterialTheme {
                App()
            }
        }
        log("app end")
    }
    log("main end")
}
