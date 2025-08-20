package com.example.app.model.data.webSocket

import Dispatcher
import Serializers
import WsClientService
import android.util.Log
import com.example.app.model.services.logger
import dto.ConnectionClosed
import dto.ConnectionOpened
import dto.GameRequest
import dto.GameResponse
import dto.HeartBeat
import dto.WsProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import serializers.WebSocketResponseSerializer.toJson
import java.util.concurrent.CountDownLatch
import kotlin.time.Duration.Companion.seconds

class GameWebSocketListener(val service: WsClientService<GameRequest, GameResponse, WsProtocol>, val serializer: Serializers, val dispatcher: Dispatcher) : WebSocketListener() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.Default)
    private val heartBeatTime = 30.seconds

    override fun onOpen(webSocket: WebSocket, response: Response) {
        scope.launch {
            Log.d(logger, "onOpen")
            service.emitToSocket(ConnectionOpened("CONNECTED!"))
            service.readFromChannelToSocket { message ->
                val json = message.toJson()
                Log.d(logger,json)
                webSocket.send(with(serializer.webSocketResponseSerializer) { json })
            }
        }
        scope.launch {
            Log.d(logger, "preparing heartbeat")
            while (true) {
                delay(heartBeatTime)
                Log.d(logger, "SENDING HEARTBEAT")
                service.emitToSocket(HeartBeat())
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(logger, "MESSAGE: $text")
        scope.launch {
            dispatcher.dispatch(with(serializer.webSocketResponseSerializer) { text.fromJson() })
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        val latch = CountDownLatch(1)
        CoroutineScope(Dispatchers.Default).launch {
            service.emitFromSocket(ConnectionClosed("FAILED!"))
            Log.d(logger, "CLOSING")
            service.close()
            job.cancelAndJoin()
            latch.countDown()
        }
        latch.await()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        scope.launch {
            service.emitFromSocket(ConnectionClosed("FAILED!"))
            Log.d(logger, "CLOSING")
        }
        scope.cancel()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(logger, "CLOSING")
        scope.cancel()
    }
}