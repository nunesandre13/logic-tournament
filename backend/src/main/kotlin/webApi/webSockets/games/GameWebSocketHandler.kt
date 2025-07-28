package webApi.webSockets.games

import Serializers
import domain.CommandResult
import kotlinx.coroutines.channels.Channel
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import services.GameService
import java.util.concurrent.CopyOnWriteArrayList
import dto.*
import kotlinx.coroutines.*
import org.http4k.core.Request
import org.http4k.websocket.WsMessage
import org.http4k.websocket.WsResponse
import toDTO
import toDomain
import java.util.logging.Logger
import kotlin.time.Duration.Companion.seconds

class GameWebSocketHandler(private val gameServices: GameService, serializer : Serializers) {

    private val heartBeatDelay = 20.seconds

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val webSocketSerializer = serializer.webSocketResponseSerializer

    private val logger = Logger.getLogger("GameWebSocketHandler")

    // por agora algo do genero
    private val connections = CopyOnWriteArrayList<Websocket>()

    fun gameWebSocket(request: Request): WsResponse {
        val channel = Channel<WebSocketResponse>(Channel.UNLIMITED)
        return websockets { webSocket ->
            connections += webSocket
            heartBeat(channel)
            sendToSocket(webSocket,channel)
            webSocket.onClose {
                connections -= webSocket
            }

            webSocket.onMessage { message ->
                println("into Logging------------------------------")
                logger.info(message.body.toString())
                val response = with(webSocketSerializer){message.body.toString().fromJson()}
                logger.info(response.toString())
                when (response) {
                    is Command -> treatCommandResponse(response, channel)
                    is Event -> treatEventResponse(response,channel)
                    is Data -> {

                    }
                    else -> {

                    }
                }
            }
            webSocket.onError {
                println("WEBSOCKET ERROR: $it")
            }
        }(request)
    }

    private fun treatEventResponse(event: Event, channel: Channel<WebSocketResponse>) {
        when (event) {
            is HeartBeat -> { logger.info("HEARTBEAT: ${event.timestamp}")}
            is MessageEvent -> TODO()
            else -> { TODO()}
        }
    }

    private fun treatCommandResponse(command: Command, channel: Channel<WebSocketResponse>) {
        scope.launch {
            if (command is GameCommandsDTO) {
                logger.info(command.toString() + "COMMAND RECEIVED")
                val result = gameServices.receiveCmd(command.toDomain())
                logger.info("Game command result: $result")
                when (result) {
                    is CommandResult.ActionResult -> {
                        // alterar interface para o play retornar um gameActionResult
                    }

                    is CommandResult.Error -> {
                        channel.send(MessageEvent("Something went wrong"))
                    }

                    is CommandResult.MatchError -> {
                        channel.send(MessageEvent("Something went wrong"))
                    }

                    is CommandResult.MatchSucceed -> {
                        logger.info("MATCH SUCCESS")
                        val gameFlow = gameServices.getStateChange(result.gameRoomId, result.gameType)
                        scope.launch {
                            gameFlow.collect { game ->
                                channel.send(game.toDTO())
                            }
                        }
                    }

                    is CommandResult.Success -> {
                        channel.send(MessageEvent("Command success"))
                    }
                }
            }
        }
    }

    private fun sendToSocket(socket: Websocket, channel: Channel<WebSocketResponse>) {
        scope.launch {
            while (isActive) {
                val json = with(webSocketSerializer) { channel.receive().toJson() }
                println(json)
                socket.send(WsMessage(json))
            }
        }
    }
    private fun heartBeat(channel: Channel<WebSocketResponse>) {
        scope.launch {
            while (true) {
                channel.send(HeartBeat())
                delay(heartBeatDelay)
            }
        }
    }
}