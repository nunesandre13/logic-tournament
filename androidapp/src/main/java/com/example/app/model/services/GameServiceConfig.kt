package com.example.app.model.services

import GameMappers
import Serializers
import com.example.app.model.data.webSocket.WebSocketService
import com.example.app.model.data.webSocket.WsGamesMessages
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import dto.WebSocketMessage
import okhttp3.OkHttpClient
import okhttp3.Request

data class GameServiceConfig(
    val webSocketClient: OkHttpClient,
    val request: Request,
    val serializer: Serializers,
    val mappers: GameMappers,
    val wsGamesMessages: WsGamesMessages,
    val service: WebSocketService<WebSocketMessage>,
    val listenerFactory: GameWebSocketListenerFactoryI
){
    companion object {
        val config = GameServiceConfig(
            webSocketClient = OkHttpClient(),
            request = Request.Builder().url("wss://example.com").build(),
            serializer = Serializers,
            mappers = GameMappers(),
            wsGamesMessages = WsGamesMessages(GameMappers()),
            service = WebSocketService(),
            listenerFactory = GameWebSocketListenerFactory()
        )
    }
}
