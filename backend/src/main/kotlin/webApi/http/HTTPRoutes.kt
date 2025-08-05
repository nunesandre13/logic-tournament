package webApi.http

import Serializers
import auth.AuthFilter
import org.http4k.routing.bind
import org.http4k.routing.routes
import services.ServicesInterfaces.IServices
import webApi.http.games.GamesHTTP
import webApi.http.user.UsersHTTP

class HTTPRoutes(services: IServices, serializer : Serializers, authFilter: AuthFilter)  {
    val routes = routes(
        "/users" bind UsersHTTP(services.userServices,serializer).routes,
        "/games" bind GamesHTTP(services.gameService).routes
    ).withFilter(authFilter.filterHTTP)
}