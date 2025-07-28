package org.example.server


fun main() {


    Runtime.getRuntime().addShutdownHook(
        Thread {
            println("Server stopped!")
        },
    )

    Thread.currentThread().join()
}
