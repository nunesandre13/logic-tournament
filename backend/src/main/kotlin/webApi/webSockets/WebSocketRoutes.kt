package webApi.webSockets

import org.http4k.routing.websockets
import webApi.webSockets.games.GamesWebSocketRoutes

class WebSocketRoutes(gamesWebSocketRoutes: GamesWebSocketRoutes){
    val routes = websockets(gamesWebSocketRoutes.routes)
}