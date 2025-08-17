package webApi.webSockets.games

import Serializers
import WebSocketChannel
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import java.util.concurrent.CopyOnWriteArrayList
import dto.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import mappers.IGameMappers
import org.http4k.core.Request
import org.http4k.websocket.WsMessage
import org.http4k.websocket.WsResponse
import services.ServicesInterfaces.IGameServices

import java.util.logging.Logger
import kotlin.time.Duration.Companion.seconds


class GameWebSocketHandler(
    private val gameServices: IGameServices,
    serializer: Serializers,
    private val mappers: IGameMappers
) {

    private val heartBeatDelay = 20.seconds

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val webSocketSerializer = serializer.webSocketResponseSerializer

    private val logger = Logger.getLogger("GameWebSocketHandler")

    private val connections = CopyOnWriteArrayList<Websocket>()

    fun gameWebSocket(request: Request): WsResponse {
        val socketChannel = WebSocketChannel<GameResponse, GameRequest, WsProtocol>()
        val messageService = WsGameMessageService(gameServices,socketChannel,mappers)
        val gameDispatcher = GameDispatcher(socketChannel)
        return websockets { webSocket ->

            connections += webSocket

            startReadingFromSocket(socketChannel,messageService)

            heartBeat {socketChannel::sendProtocolToSocket}

            sendToSocket(webSocket,socketChannel.fromApp())

            webSocket.onClose {
                connections -= webSocket
            }
            webSocket.onMessage { message ->
                onMessage(message, gameDispatcher)
            }
            webSocket.onError {
                println("WEBSOCKET ERROR: $it")
            }
        }(request)
    }

    fun startReadingFromSocket(socketChannel: WebSocketChannel<GameResponse, GameRequest, WsProtocol>,messageService: WsGameMessageService) {
        scope.launch {
            socketChannel.fromSocket().collect { message ->
                messageService.onRequest(message)
            }
        }
        scope.launch {
            socketChannel.fromSocketProtocol().collect { message ->
                messageService.onProtocol(message)
            }
        }
    }

    fun onMessage(message: WsMessage, gameDispatcher: GameDispatcher) {
        scope.launch {
            println("into Logging------------------------------")
            logger.info(message.body.toString())
            val response = with(webSocketSerializer) { message.body.toString().fromJson() }
            gameDispatcher.dispatch(response)
        }
    }


    private fun sendToSocket(socket: Websocket, flow: Flow<GameResponse>) {
        scope.launch {
            flow.collect { response ->
                val json = with(webSocketSerializer) { response.toJson() }
                println(json)
                socket.send(WsMessage(json))
            }
        }
    }

    private fun heartBeat(send: suspend (HeartBeat) -> Unit) {
        scope.launch {
            while (true) {
                delay(heartBeatDelay)
                send(HeartBeat())
            }
        }
    }

}