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
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import toDTO

import kotlin.time.Duration.Companion.seconds

const val logger = "MY_APP"

class GameService(config: GameServiceConfig){
    private val webSocketClient: OkHttpClient = config.webSocketClient
    private val request: Request = config.request
    private val serializer: Serializers = config.serializer
    private val mappers: GameMappers = config.mappers
    private val listenerFactory = GameWebSocketListenerFactory()

    data class WsClient(val wsClient: WebSocket, val wsService: WsClientService<GameRequest, GameResponse, WsProtocol>, val scopeJob: Job)

    private var socketRef : WsClient? = null

    private val mutex = Mutex()

    private val hearBeatDelay = 30.seconds

    private val handlers = GamesHandlers(mappers)


    fun socketCommunication(scope: CoroutineScope, wsService: WsClientService<GameRequest, GameResponse, WsProtocol>){
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


    suspend fun close(){
        val result = mutex.withLock {
            val client = socketRef
            socketRef = null
            client
        }
        if (result != null) {
            result.scopeJob.cancelAndJoin()
            result.wsClient.close(1000, null)
        }
    }

    suspend fun connect() {
        val wsService = WsClientService<GameRequest, GameResponse, WsProtocol>()
        val job = SupervisorJob()
        val scope = CoroutineScope( job + Dispatchers.Default)
        Log.d(logger, "opening the socket")
        val dispatcher = GameDispatcher(wsService)
        val listener = listenerFactory.create(wsService,serializer,dispatcher)
        val ws = webSocketClient.newWebSocket(request, listener)
        val client = WsClient(ws, wsService,job)
        socketCommunication(scope,wsService)
        mutex.withLock {
            socketRef = client
        }
        Log.d(logger, "connection made" )
    }

    suspend fun sendCommand(command: GameCommands, roomId: Id) {
        mutex.withLock {
            Log.d(logger, "sending command $command")
            socketRef?.wsService?.emitToSocket(mappers.toDTO(command, roomId.toDTO()))
        }

    }

    suspend fun requestGame(player: Player, gameType: GameType) {
        mutex.withLock {
            socketRef?.wsService?.emitToSocket(
                GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO(
                    player.toDTO(),
                    gameType
                )
            )
        }
    }
}