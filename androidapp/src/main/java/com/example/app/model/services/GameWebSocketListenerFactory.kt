package com.example.app.model.services

import Serializers
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import com.example.app.model.data.webSocket.GameWebSocketListener
import WebSocketChannel
import okhttp3.WebSocketListener

class GameWebSocketListenerFactory:GameWebSocketListenerFactoryI {
    override fun create(service: WebSocketChannel<WebSocketMessage>, serializer: Serializers): WebSocketListener {
        return GameWebSocketListener(service, serializer)
    }
}