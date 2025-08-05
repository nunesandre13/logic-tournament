package auth

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.websocket.WsFilter
import org.http4k.websocket.WsHandler
import org.http4k.websocket.WsMessage
import org.http4k.websocket.WsResponse

class AuthFilter(private val authService: AuthService) {

    val filterHTTP = Filter { next: HttpHandler ->
        { request ->
            val token = request.header("Authorization")?.substringAfter("Bearer ")
            if (token == null) {
                Response(Status.UNAUTHORIZED).body("Token não fornecido.")
            } else {
                try {
                    authService.verifyAccessToken(token)
                    next(request)
                } catch (e: Exception) {
                    Response(Status.UNAUTHORIZED).body("Token inválido ou expirado.")
                }
            }
        }
    }

    val filterWebSocket = WsFilter { next: WsHandler ->
        { request ->
            val token = request.header("Authorization")?.substringAfter("Bearer ")
            if (token == null) {
                wsResponseMaker("Token não fornecido.")
            } else {
                try {
                    authService.verifyAccessToken(token)
                    next(request)
                } catch (e: Exception) {
                    wsResponseMaker(e.message.toString())
                }
            }

        }
    }

    private fun wsResponseMaker(message: String): WsResponse = WsResponse { webSocket ->
        webSocket.send(WsMessage(message))
    }
}
