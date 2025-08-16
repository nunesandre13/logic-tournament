package com.example.app.model.data.webSocket

import Serializers
import android.util.Log
import com.example.app.model.services.logger
import dto.ConnectionClosed
import dto.ConnectionOpened
import dto.HeartBeat
import dto.WebSocketMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import kotlin.time.Duration.Companion.seconds

class GameWebSocketListener(val service: WebSocketService<WebSocketMessage>, val serializer: Serializers) : WebSocketListener() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val heartBeatTime = 30.seconds

    init {
        Log.d("GameWebSocketListener", "init")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("GameWebSocketListener", "onOpen")
        scope.launch {
            Log.d(logger, "onOpen")
            service.sendToApp(ConnectionOpened("CONNECTED!"))
            service.fromApp().collect { response ->
                val json = with(serializer.webSocketResponseSerializer) { response.toJson() }
                webSocket.send(json)
            }
        }
        scope.launch {
            Log.d(logger, "preparing heartbeat")
            while (true) {
                delay(heartBeatTime)
                Log.d(logger, "SENDING HEARTBEAT")
                service.sendToApp(HeartBeat())
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(logger, "MESSAGE: $text")
        scope.launch {
            service.sendToApp(with(serializer.webSocketResponseSerializer) { text.fromJson() })
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        scope.launch {
            service.sendToApp(ConnectionClosed("CLOSING!"))
            Log.d(logger, "CLOSING")
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        scope.launch {
            service.sendToApp(ConnectionClosed("FAILED!"))
            Log.d(logger, "CLOSING")
        }
        scope.cancel()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(logger, "CLOSING")
        scope.cancel()
    }
}