package com.example.app.model.apiService

import WebSocketMessageService
import domain.games.GameCommands
import dto.GameCommandsDTO
import dto.GameDataDTO
import dto.GameEventDTO
import dto.ProtocolMessage
import dto.WebSocketMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class WsGamesMessages : WebSocketMessageService<GameCommandsDTO, GameDataDTO, GameEventDTO> {

    private val _events = MutableSharedFlow<GameEvent>()
    val events: SharedFlow<GameEventDTO> = _events

    private val _data = MutableSharedFlow<GameData>()
    val data: SharedFlow<GameDataDTO> = _data

    private val _commands = MutableSharedFlow<GameCommands>()
    val commands: SharedFlow<GameCommands> = _commands

    override fun onCommand(command: GameCommandsDTO) {
        val domainCommand = command.toDomain()
        _commands.tryEmit(domainCommand)
    }

    override fun onData(data: GameDataDTO) {
        val domainData = data.toDomain()
        _data.tryEmit(domainData)
    }
    override fun onEvent(event: GameEventDTO) {
        val domainEvent = event.toDomain()
        _events.tryEmit(domainEvent)
    }
    override fun onProtocol(message: ProtocolMessage) {
        TODO("Not yet implemented")
    }

    override fun onOther(message: WebSocketMessage) {
        TODO("Not yet implemented")
    }



}
