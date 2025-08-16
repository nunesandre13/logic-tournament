package webApi.webSockets

import auth.AuthFilter
import org.http4k.routing.websockets
import webApi.webSockets.games.GamesWebSocketRoutes

class WebSocketRoutes(gamesWebSocketRoutes: GamesWebSocketRoutes, authFilter: AuthFilter){
    val routes = websockets(gamesWebSocketRoutes.routes) //.withFilter(authFilter.filterWebSocket)
}