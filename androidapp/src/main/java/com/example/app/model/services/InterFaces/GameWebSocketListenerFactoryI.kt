package com.example.app.model.services.InterFaces

import Serializers
import WebSocketChannel
import okhttp3.WebSocketListener

interface GameWebSocketListenerFactoryI {
    fun create(service: WebSocketChannel<WebSocketMessage>, serializer: Serializers): WebSocketListener
}