@file:OptIn(ExperimentalUnitApi::class)

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.compose.ui.graphics.Color

@Composable
fun Hello(name: String) =
    Text("Hello $name", fontSize = 26.sp /*TextUnit(26.0F, TextUnitType.Sp)*/)

@Composable
fun CounterButton(value: Int, onClick: ()->Unit) =
    Button(onClick = onClick) {
        Text("$value")
    }

@Composable
@Preview
fun App2() {
    var counter by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .background(Color.Cyan)
            .padding(10.dp)
            .background(Color.Yellow)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CounterButton(counter) { ++counter }
        Hello("ISEL")
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme { App2() }
    }
}
