package pt.isel.tds.ttt.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.*

class StopWatch {
    val formattedTime: String
        get() {
            val minutes = time / 60000
            val seconds = time % 60000 / 1000
            val dec = time % 1000 / 100
            return "%02d:%02d.%01d".format(minutes,seconds,dec)
        }

    var time by mutableStateOf(0L)
        private set
    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private var running = false

    fun reset() {
        time = 0
    }
    fun start() {
        if (running) return
        scope.launch{
            running = true
            println("start(): ${Thread.currentThread().name}")
            while(running) {
                delay(100)
                time += 100 // TODO: cumulative error
            }
        }
    }
    fun pause() {
        running = false
    }
}