package com.example.app.model.data.webSocket

import Serializers
import dto.ConnectionClosed
import dto.ConnectionOpened
import dto.WebSocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class GameWebSocketListener(val service: WebSocketService<WebSocketMessage>, val serializer: Serializers) : WebSocketListener() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        scope.launch {
            service.sendToApp(ConnectionOpened("CONNECTED!"))
            service.fromApp().collect { response ->
                val json = with(serializer.webSocketResponseSerializer) { response.toJson() }
                webSocket.send(json)
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        scope.launch {
            service.sendToApp(with(serializer.webSocketResponseSerializer) { text.fromJson() })
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        scope.launch {
            service.sendToApp(ConnectionClosed("CLOSING!"))
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        scope.launch {
            service.sendToApp(ConnectionClosed("FAILED!"))
        }
        scope.cancel()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        scope.cancel()
    }
}