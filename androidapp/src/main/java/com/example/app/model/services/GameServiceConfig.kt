package com.example.app.model.services

import GameMappers
import Serializers
import WebSocketChannel
import com.example.app.model.data.webSocket.GamesHandlers
import com.example.app.model.services.InterFaces.GameWebSocketListenerFactoryI
import okhttp3.OkHttpClient
import okhttp3.Request

data class GameServiceConfig(
    val webSocketClient: OkHttpClient,
    val request: Request,
    val serializer: Serializers,
    val mappers: GameMappers
){
    companion object {
        val config = GameServiceConfig(
            webSocketClient = OkHttpClient.Builder().build(),
            request = Request.Builder().url("ws://10.0.2.2:9000/ws/games").build(),
            serializer = Serializers,
            mappers = GameMappers()
        )
    }
}
