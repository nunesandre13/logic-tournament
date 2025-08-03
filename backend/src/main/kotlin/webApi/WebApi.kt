package webApi

import Serializers
import mappers.IGameMappers
import org.http4k.routing.bindHttp
import org.http4k.routing.bindWs
import org.http4k.routing.poly
import services.ServicesInterfaces.IServices
import webApi.http.HTTPRoutes
import webApi.webSockets.WebSocketRoutes
import webApi.webSockets.games.GameWebSocketHandler
import webApi.webSockets.games.GamesWebSocketRoutes

class WebApi(services: IServices, serializers: Serializers, gameMapper: IGameMappers ) {
    private val wsGamesRoutes = GamesWebSocketRoutes(GameWebSocketHandler(services.gameService,serializers, gameMapper))
    private val wsRoutes = WebSocketRoutes(wsGamesRoutes)
    private val httpRoutes = HTTPRoutes(services,serializers)
    val routes = poly(
        "/ws" bindWs wsRoutes.routes,
        "/http" bindHttp httpRoutes.routes
    )
}