package com.example.app.model

import GameMappers
import Serializers
import com.example.app.model.data.webSocket.GameWebSocketListener
import com.example.app.model.data.webSocket.WebSocketMessageDispatcher
import com.example.app.model.data.webSocket.WebSocketService
import com.example.app.model.data.webSocket.WsGamesMessages
import domain.Id
import domain.Player
import domain.games.GameCommands
import domain.games.GameType
import dto.GameCommandsDTO
import dto.WebSocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import toDTO

class GameRepository(
    private val webSocketClient: OkHttpClient,
    private val request: Request,
    private val service: WebSocketService<WebSocketMessage>,
    private val serializer: Serializers) {
    private val mappers = GameMappers()
    private val wsGamesMessages = WsGamesMessages(mappers)
    private val dispatcher = WebSocketMessageDispatcher(wsGamesMessages)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            service.fromSocket().collect { message ->
                dispatcher.dispatch(message)
            }
        }
    }

    val commands = wsGamesMessages.commands

    val data = wsGamesMessages.data

    val events = wsGamesMessages.events

    fun connect() {
        webSocketClient.newWebSocket(request, GameWebSocketListener(service, serializer))
    }

    suspend fun sendCommand(command: GameCommands, roomId: Id) {
        service.sendToSocket(mappers.toDTO(command, roomId.toDTO()))
    }

    suspend fun requestGame(player: Player, gameType: GameType) {
        service.sendToSocket(GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO(player.toDTO(), gameType))
    }
}
