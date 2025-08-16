package com.example.app.model.data.webSocket

import WebSocketMessageDispatcher
import WebSocketMessageService
import android.util.Log
import com.example.app.model.services.logger
import domain.games.GameCommands
import domain.games.GameData
import domain.games.GameEvent
import dto.GameCommandsDTO
import dto.GameDataDTO
import dto.GameEventDTO
import dto.ProtocolMessage
import dto.WebSocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import mappers.IGameMappers

class WsGamesMessages(private val mappers: IGameMappers) : WebSocketMessageService<GameCommandsDTO, GameDataDTO, GameEventDTO>,
    WebSocketMessageDispatcher {
    override fun dispatch(message: WebSocketMessage) {
        when (message) {
            is GameCommandsDTO -> onCommand(message)
            is GameDataDTO -> onData(message)
            is GameEventDTO -> onEvent(message)
            is ProtocolMessage -> onProtocol(message)
            else -> onOther(message)
        }
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _events = MutableSharedFlow<GameEvent>()
    val events: SharedFlow<GameEvent> = _events

    private val _data = MutableSharedFlow<GameData>()
    val data: SharedFlow<GameData> = _data

    private val _commands = MutableSharedFlow<GameCommands>()
    val commands: SharedFlow<GameCommands> = _commands

    override fun onCommand(command: GameCommandsDTO) {
        val domainCommand = mappers.toDomain(command)
        Log.d(logger, "Game command: $domainCommand")
        scope.launch {
            _commands.emit(domainCommand)
        }
    }

    override fun onData(data: GameDataDTO) {
        val domainData = mappers.toDomain(data)
        Log.d(logger, "Game data: $domainData")
        scope.launch {
            _data.emit(domainData)
        }
    }
    override fun onEvent(event: GameEventDTO) {
        val domainEvent = mappers.toDomain(event)
        Log.d(logger, "Game event: $domainEvent")
        scope.launch {
            _events.emit(domainEvent)
        }
    }

    override fun onProtocol(message: ProtocolMessage) {

    }

    override fun onOther(message: WebSocketMessage) {

    }

}