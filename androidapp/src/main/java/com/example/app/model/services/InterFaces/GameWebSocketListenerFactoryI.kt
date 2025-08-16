package com.example.app.model.services.InterFaces

import Serializers
import com.example.app.model.data.webSocket.GameWebSocketListener
import com.example.app.model.data.webSocket.WebSocketService
import dto.WebSocketMessage
import okhttp3.WebSocketListener

interface GameWebSocketListenerFactoryI {
    fun create(service: WebSocketService<WebSocketMessage>, serializer: Serializers): WebSocketListener
}