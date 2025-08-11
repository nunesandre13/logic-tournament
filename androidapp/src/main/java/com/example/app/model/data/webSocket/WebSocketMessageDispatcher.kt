package com.example.app.model.data.webSocket

import WebSocketMessageService
import dto.GameCommandsDTO
import dto.GameDataDTO
import dto.GameEventDTO
import dto.ProtocolMessage
import dto.WebSocketMessage

class WebSocketMessageDispatcher(
    private val service: WebSocketMessageService<GameCommandsDTO, GameDataDTO, GameEventDTO>
) {
    fun dispatch(message: WebSocketMessage) {
        when (message) {
            is GameCommandsDTO -> service.onCommand(message)
            is GameDataDTO -> service.onData(message)
            is GameEventDTO -> service.onEvent(message)
            is ProtocolMessage -> service.onProtocol(message)
            else -> service.onOther(message)
        }
    }
}
