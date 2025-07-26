package webApi.webSockets.games

import core.Command
import core.Game
import domain.CommandResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import org.http4k.websocket.WsMessage
import serialization.AppJson
import services.GameService
import java.util.concurrent.CopyOnWriteArrayList
import kotlinx.serialization.encodeToString
import org.http4k.core.Request

import org.http4k.websocket.WsResponse

class GameWebSocketHandler(private val gameServices: GameService) {

    // por agora algo do genero
    private val connections = CopyOnWriteArrayList<Websocket>()

    fun gameWebSocket(request: Request): WsResponse {
        return websockets { webSocket ->
            connections += webSocket

            webSocket.onClose {
                connections -= webSocket
            }

            webSocket.onMessage { message ->
                val command = AppJson.decodeFromString<Command>(message.body.toString())
                when (val result = gameServices.receiveCmd(command)) {
                    is CommandResult.ActionResult -> {
                        // alterar interface para o play retornar um gameActionResult
                    }
                    is CommandResult.Error -> {
                        webSocket.send(WsMessage("Something went wrong"))
                    }
                    is CommandResult.MatchError -> {
                        webSocket.send(WsMessage("Something went wrong"))
                    }
                    is CommandResult.MatchSucceed -> {
                        val gameFlow = gameServices.getStateChange(result.gameRoomId, result.gameType)
                        CoroutineScope(Dispatchers.IO).launch {
                            gameFlow.collect { game ->
                                webSocket.send(WsMessage(AppJson.encodeToString(game)))
                            }
                        }
                    }
                    is CommandResult.Success -> {
                        webSocket.send(WsMessage("Command with success."))
                    }
                }
            }

            webSocket.onError {
                println("Something went wrong")
            }
        }(request)
    }
}