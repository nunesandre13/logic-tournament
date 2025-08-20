package webApi.webSockets.games

import Serializers
import WsServerService
import domain.games.MatchResult
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import java.util.concurrent.CopyOnWriteArrayList
import dto.*
import kotlinx.coroutines.*
import mappers.IGameMappers
import org.http4k.core.Request
import org.http4k.websocket.WsMessage
import org.http4k.websocket.WsResponse
import serializers.WebSocketResponseSerializer.toJson
import services.ServicesInterfaces.IGameServices

import java.util.logging.Logger
import kotlin.time.Duration.Companion.seconds


class GameWebSocketHandler(
    private val gameServices: IGameServices,
    private val serializer: Serializers,
    private val mappers: IGameMappers
) {

    private val heartBeatDelay = 20.seconds

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val webSocketSerializer = serializer.webSocketResponseSerializer

    private val logger = Logger.getLogger("GameWebSocketHandler")

    private val connections = CopyOnWriteArrayList<Websocket>()

    fun gameWebSocket(request: Request): WsResponse {
        val wsService = WsServerService<GameRequest, GameResponse, WsProtocol>()
        val gameDispatcher = GameDispatcher(wsService)
        val handlers = WsServerGameHandlers(gameServices, mappers)

        return websockets { webSocket ->

            connections += webSocket

            // envia tudo para o socket
            scope.launch {
                wsService.readFromChannelToSocket{ message ->
                    webSocket.send(WsMessage(message.toJson()))
                }
            }

            scope.launch {
                handlers.gameState.collect { gameState ->
                    wsService.emitToSocket(gameState)
                }
            }

            // envia heatBeat para o channel para ser enviado para o socket
            scope.launch {
                while (isActive) {
                    delay(heartBeatDelay)
                    wsService.emitToSocket(HeartBeat())
                }
            }

            // recebe um request e produz uma resposta
            scope.launch {
                wsService.onRequest{ request ->
                    val response = handlers.onRequest(request)
                    wsService.emitToSocket(response)
                    scope.launch {
                        logger.info("flushing the functions")
                        handlers.flushAfterResponse()
                    }
                    logger.info("launch to flush function begin")
                }
            }

            scope.launch {
                wsService.onProtocol { protocol ->
                    handlers.onProtocol(protocol)
                }
            }

            webSocket.onClose {
                connections -= webSocket
            }
            webSocket.onMessage { message ->
                scope.launch {
                    gameDispatcher.dispatch(with(webSocketSerializer) { message.bodyString().fromJson() })
                }
            }
            webSocket.onError { error ->
                logger.info(error.toString())
            }
        }(request)
    }

}