package webApi.webSockets

import org.http4k.routing.poly
import org.http4k.server.Jetty
import org.http4k.server.asServer
import services.GameFactory
import services.GameRoomManager
import services.GameService
import services.MatchMakingService
import webApi.webSockets.games.GameWebSocketHandler
import webApi.webSockets.games.GamesWebSocketRoutes

fun main(){
    val gameFactory = GameFactory()
    val gameRoomManager = GameRoomManager()
    val matchMaking = MatchMakingService(gameRoomManager, gameFactory)
    val services = GameService(matchMaking, gameRoomManager)
    val gamesHandlers = GameWebSocketHandler(services)
    val gameRoutes = GamesWebSocketRoutes(gamesHandlers)
    val routes = WebSocketRoutes(gameRoutes)
    poly(routes.routes).asServer(Jetty(6000)).start()
    Thread.currentThread().join()
}