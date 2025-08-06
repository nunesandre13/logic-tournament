package server

import GameMappers
import Serializers
import auth.AuthService
import data.DataMem.DataMem
import org.http4k.server.Jetty
import org.http4k.server.asServer
import services.Services
import webApi.WebApi


fun main() {
    val data = DataMem()
    val services = Services(data)
    val webApi = WebApi(services, Serializers,GameMappers(), AuthService("MY_SECRET", data.authData, "ANDRENUNESAPI"))
    webApi.routes.asServer(Jetty(9000)).start()
    Thread.currentThread().join()
}
