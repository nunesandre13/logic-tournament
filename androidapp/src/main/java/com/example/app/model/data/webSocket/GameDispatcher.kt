package com.example.app.model.data.webSocket

import Dispatcher
import WsClientService
import android.util.Log
import com.example.app.model.services.logger
import dto.GameRequest
import dto.GameResponse
import dto.WsProtocol
import dto.WsRequest
import dto.WsResponse

class GameDispatcher(private val wsService: WsClientService<GameRequest, GameResponse, WsProtocol>): Dispatcher() {

    override suspend fun dispatchRequests(message: WsRequest) {
        Log.d(logger,"THIS SHOULD NOT BE CALLED")
    }

    override suspend fun dispatchResponse(message: WsResponse) {
        when (message) {
            is GameResponse -> {
                Log.d(logger,"Emit : $message")
                wsService.emitFromSocket(message)
            }
            else -> Log.d(logger,"THERE IS THIS TYPE OF RESPONSE IMPLEMENTED")
        }
    }

    override suspend fun dispatchProtocol(message: WsProtocol) {
        Log.d(logger,"Emit : $message")
        wsService.emitFromSocket(message)
    }
}