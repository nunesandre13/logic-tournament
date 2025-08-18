package com.example.app.model.services

import Dispatcher
import Serializers
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import com.example.app.model.data.webSocket.GameWebSocketListener
import WebSocketChannel
import WsClientService
import dto.GameRequest
import dto.GameResponse
import dto.WsProtocol
import okhttp3.WebSocketListener

class GameWebSocketListenerFactory:GameWebSocketListenerFactoryI {
    override fun create(service: WsClientService<GameRequest, GameResponse, WsProtocol>, serializer: Serializers, dispatcher: Dispatcher): WebSocketListener {
        return GameWebSocketListener(service, serializer,dispatcher)
    }
}