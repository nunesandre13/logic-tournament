package com.example.app.model.services

import GameMappers
import Serializers
import android.util.Log
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import WebSocketChannel
import com.example.app.model.data.webSocket.WsGamesMessages
import domain.Id
import domain.Player
import domain.games.GameCommands
import domain.games.GameType
import dto.GameCommandsDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import toDTO
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.atomic.AtomicInteger

const val logger = "MY_APP"

class GameService(config: GameServiceConfig) {

    private val webSocketClient: OkHttpClient = config.webSocketClient
    private val request: Request = config.request
    private val serializer: Serializers = config.serializer
    private val mappers: GameMappers = config.mappers
    private val wsGamesMessages: WsGamesMessages = config.wsGamesMessages
    private val service: WebSocketChannel<WebSocketMessage> = config.service
    private val listenerFactory: GameWebSocketListenerFactoryI = config.listenerFactory
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val clients = ConcurrentLinkedDeque<WebSocket>()
    private var webSocketListener: WebSocketListener? = null

    init {
        scope.launch {
            service.fromSocket().collect { message ->
                Log.d(logger, "Game service: $message, collectinggggg")
                wsGamesMessages.dispatch(message)
            }
        }
    }

    val commands = wsGamesMessages.commands

    private val connections = AtomicInteger(0)

    val data = wsGamesMessages.data

    val events = wsGamesMessages.events

    fun connect() {
        Log.d(logger, "openning the socket")

        val listener = listenerFactory.create(service,serializer)
        webSocketListener = listener
        val ws = webSocketClient.newWebSocket(request, listener )
        clients.add(ws)
        connections.incrementAndGet()
        Log.d(logger, "connection made" + connections.get())

    }

    suspend fun sendCommand(command: GameCommands, roomId: Id) {
        Log.d(logger, "sending command $command")
        service.sendToSocket(mappers.toDTO(command, roomId.toDTO()))

    }

    suspend fun requestGame(player: Player, gameType: GameType) {
        service.sendToSocket(GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO(player.toDTO(), gameType))
    }
}