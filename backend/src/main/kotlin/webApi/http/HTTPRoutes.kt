package webApi.http

import Serializers
import auth.AuthFilter
import auth.AuthService
import org.http4k.routing.bind
import org.http4k.routing.routes
import services.ServicesInterfaces.IServices
import webApi.http.games.GamesHTTP
import webApi.http.user.UsersHTTP

class HTTPRoutes(val services: IServices, serializer : Serializers, authFilter: AuthFilter, authService: AuthService)  {
    private val  userHTTP = UsersHTTP(services.userServices, serializer, authService)
    val routes = routes(
        "/users" bind userHTTP.nonProtectedRoutes,
        "/users" bind userHTTP.protectedRoutes.withFilter(authFilter.filterHTTP),
        "/games" bind GamesHTTP(services.gameService).routes
    )
}