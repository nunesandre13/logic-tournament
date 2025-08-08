package com.example.app.model.data

import com.example.app.model.apiService.WebSocketService
import dto.WebSocketMessage
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class GameWebSocketListener(private val service: WebSocketService<WebSocketMessage>) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("Ligado ao servidor WebSocket")

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Recebido texto: $text")

    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        println(" A fechar ligação: $code / $reason")
        webSocket.close(1000, null)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println(" Erro no WebSocket: ${t.message}")
    }
}