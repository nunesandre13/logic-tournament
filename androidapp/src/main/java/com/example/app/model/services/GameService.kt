package com.example.app.model.services

import GameMappers
import Serializers
import WsClientService
import android.util.Log
import com.example.app.model.data.webSocket.GameDispatcher
import com.example.app.model.data.webSocket.GamesHandlers
import domain.Id
import domain.Player
import domain.games.GameCommands
import domain.games.GameType
import dto.GameCommandsDTO
import dto.GameRequest
import dto.GameResponse
import dto.HeartBeat
import dto.WsProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import toDTO
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.time.Duration.Companion.seconds

const val logger = "MY_APP"

class GameService(config: GameServiceConfig){
    private val webSocketClient: OkHttpClient = config.webSocketClient
    private val request: Request = config.request
    private val serializer: Serializers = config.serializer
    private val mappers: GameMappers = config.mappers
    private val listenerFactory = GameWebSocketListenerFactory()

    private val wsService = WsClientService<GameRequest, GameResponse, WsProtocol>()

    private val connection = ConcurrentLinkedDeque<WebSocket>()

    private val hearBeatDelay = 30.seconds

    private val handlers = GamesHandlers(mappers)
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    init {
        scope.launch {
            wsService.onProtocol { protocol ->
                Log.d(logger, protocol.toString())
                handlers.onProtocol(protocol)
            }
        }
        scope.launch {
            wsService.onResponse { response ->
                Log.d(logger, response.toString())
                handlers.onGameResponse(response)
            }
        }
        scope.launch {
            while (isActive) {
                delay(hearBeatDelay)
                wsService.emitToSocket(HeartBeat())
            }
        }
    }

    val gameEvents = handlers.events

    val gameData = handlers.data

    private val dispatcher = GameDispatcher(wsService)

    fun connect() {
        Log.d(logger, "opening the socket")
        val listener = listenerFactory.create(wsService,serializer,dispatcher)
        val ws = webSocketClient.newWebSocket(request, listener)
        connection.add(ws)
        Log.d(logger, "connection made" )
    }

    suspend fun sendCommand(command: GameCommands, roomId: Id) {
        Log.d(logger, "sending command $command")
        wsService.emitToSocket(mappers.toDTO(command, roomId.toDTO()))

    }

    suspend fun requestGame(player: Player, gameType: GameType) {
        wsService.emitToSocket(GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO(player.toDTO(), gameType))
    }
}