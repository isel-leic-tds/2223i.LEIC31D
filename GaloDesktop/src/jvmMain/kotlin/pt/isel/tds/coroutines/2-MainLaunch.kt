package pt.isel.tds.coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    log("Before")
    val startTime = System.currentTimeMillis()
    runBlocking {
        log("Inside")
        launch {
            log("Inside2")
            printHello()
        }
        printHello()
    }
    log("After")
    println("Time= ${System.currentTimeMillis()-startTime}")
}
