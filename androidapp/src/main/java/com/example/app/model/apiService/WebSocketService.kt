package com.example.app.model.apiService

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.consumeAsFlow

class WebSocketService<T>(
    private val bufferCapacity: Int = Channel.UNLIMITED
) {
    private val messagesFromSocket = MutableSharedFlow<T>()
    private val messagesToSocket = Channel<T>(bufferCapacity)

    // App → Socket
    suspend fun sendToSocket(message: T) = messagesToSocket.send(message)
    fun fromApp(): Flow<T> = messagesToSocket.consumeAsFlow()

    // Socket → App
    suspend fun sendToApp(message: T) = messagesFromSocket.emit(message)
    fun fromSocket(): SharedFlow<T> = messagesFromSocket

    fun close() {
        messagesToSocket.close()
    }
}
