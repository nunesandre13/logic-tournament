package webApi.webSockets.games

import domain.CommandResult
import dto.Command
import dto.Data
import dto.Event
import dto.WebSocketResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import org.http4k.websocket.WsMessage
import serialization.AppJson
import services.GameService
import java.util.concurrent.CopyOnWriteArrayList
import kotlinx.serialization.encodeToString
import domain.games.GameCommands
import org.http4k.core.Request
import org.http4k.websocket.WsResponse
import java.util.logging.Logger


class GameWebSocketHandler(private val gameServices: GameService) {

    private val logger = Logger.getLogger("GameWebSocketHandler")
    // por agora algo do genero
    private val connections = CopyOnWriteArrayList<Websocket>()

    fun gameWebSocket(request: Request): WsResponse {

        val channel = Channel<WebSocketResponse>(Channel.UNLIMITED)

        return websockets { webSocket ->
            connections += webSocket

            webSocket.onClose {
                connections -= webSocket
            }

            webSocket.onMessage { message ->
                logger.info(message.body.toString())
                when (val response = AppJson.decodeFromString<WebSocketResponse>(message.body.toString())){
                    is Command -> { treatCommandResponse(response, webSocket) }
                    is Event -> {}
                    is Data -> {}
                    else -> Unit // nao devia ser necessario
                }
            }
            webSocket.onError {
                println("WEBSOCKET ERROR: $it")
            }
        }(request)
    }

    private fun treatCommandResponse(command: Command, webSocket: Websocket) {
        if (command is GameCommands)
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
}