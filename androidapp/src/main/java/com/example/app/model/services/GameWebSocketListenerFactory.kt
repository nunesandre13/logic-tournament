package com.example.app.model.services

import Serializers
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import com.example.app.model.data.webSocket.GameWebSocketListener
import com.example.app.model.data.webSocket.WebSocketService
import dto.WebSocketMessage
import okhttp3.WebSocketListener

class GameWebSocketListenerFactory:GameWebSocketListenerFactoryI {
    override fun create(service: WebSocketService<WebSocketMessage>, serializer: Serializers): WebSocketListener {
        return GameWebSocketListener(service, serializer)
    }
}