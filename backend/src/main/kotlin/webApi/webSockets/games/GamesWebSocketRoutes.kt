package webApi.webSockets.games

import org.http4k.routing.websocket.bind
import org.http4k.routing.websockets

class GamesWebSocketRoutes(gameWebSocketHandler: GameWebSocketHandler){
    val routes = websockets(
        "/games" bind gameWebSocketHandler::gameWebSocket
    )
}