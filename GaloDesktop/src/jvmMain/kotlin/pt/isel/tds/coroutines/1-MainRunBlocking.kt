package pt.isel.tds.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// Edit Configurations -> VM options: -Dkotlinx.coroutines.debug
fun log(txt: String) =
    println("$txt: ${Thread.currentThread().name}")

fun main() {
    log("Before")
    runBlocking {
        log("Inside")
        printHello()
    }
    log("After")
}

suspend fun printHello() {
    log("printHello")
    delay(3000)
    println("Hello")
}
