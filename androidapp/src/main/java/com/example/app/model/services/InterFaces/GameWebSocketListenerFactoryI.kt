package com.example.app.model.services.InterFaces

import Dispatcher
import Serializers
import WebSocketChannel
import WsClientService
import dto.GameRequest
import dto.GameResponse
import dto.WsProtocol
import okhttp3.WebSocketListener

interface GameWebSocketListenerFactoryI {
    fun create(service: WsClientService<GameRequest, GameResponse, WsProtocol>, serializer: Serializers,dispatcher: Dispatcher): WebSocketListener
}