package com.example.app.model.services

import Serializers
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import com.example.app.model.data.webSocket.GameWebSocketListener
import com.example.app.model.data.webSocket.WebSocketService
import dto.WebSocketMessage

class GameWebSocketListenerFactory:GameWebSocketListenerFactoryI {
    override fun create(service: WebSocketService<WebSocketMessage>, serializer: Serializers): GameWebSocketListener {
        return GameWebSocketListener(service, serializer)
    }
}