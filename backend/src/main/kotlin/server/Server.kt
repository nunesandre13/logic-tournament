package org.example.server

import org.example.data.DataMem.PlayerDataMem
import org.example.webApi.PlayerWebApi
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 9000
    val data = PlayerDataMem()
    val services = PlayerService(data)
    val webApi = PlayerWebApi(services)
    val app =
        routes("player" bind webApi.routes)
    val jettyServer = app.asServer(Jetty(port)).start()

    Runtime.getRuntime().addShutdownHook(
        Thread {
            jettyServer.stop()
            println("Server stopped!")
        },
    )

    Thread.currentThread().join()
}
