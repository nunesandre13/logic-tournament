package com.example.app.model.apiService

import Serializers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class WebSocketService<T>(
    private val serializable: Serializers,
    private val scope: CoroutineScope
) {
    private val incomingMessages = MutableSharedFlow<T>()

    private val sendChannel = Channel<T>(Channel.UNLIMITED)

    val incoming: SharedFlow<T> get() = incomingMessages

    init {
        scope.launch {
            incomingMessages.collect {
                incomingMessages.emit(it)
            }
        }
    }

    suspend fun sendMessage(message: T) {
        sendChannel.send(message)
    }

    fun onMessage(text: String) {
        val message = json.decodeFromString(serializer, text)
        scope.launch {
            incomingMessages.emit(message)
        }
    }

    fun close() {
        sendChannel.close()
        webSocket.close(1000, "Fechando conexão")
        scope.cancel()
    }
}
