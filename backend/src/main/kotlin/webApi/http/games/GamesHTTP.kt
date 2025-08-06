package webApi.http.games

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import services.ServicesInterfaces.IGameServices
import webApi.http.runCatchingResponse

class GamesHTTP(private val services: IGameServices) {
    val routes = routes(
        "/" bind Method.GET to ::listAllGames,
    )

    private fun listAllGames(request: Request) = runCatchingResponse(OK){
        Json.encodeToString(services.listAllGames())
    }
}