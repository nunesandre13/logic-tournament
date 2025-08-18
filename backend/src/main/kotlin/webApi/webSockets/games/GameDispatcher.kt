package webApi.webSockets.games

import Dispatcher
import WsServerService

import dto.GameRequest
import dto.GameResponse

import dto.WsProtocol
import dto.WsRequest
import dto.WsResponse
import org.slf4j.LoggerFactory


class GameDispatcher(val wsService : WsServerService<GameRequest, GameResponse, WsProtocol>): Dispatcher() {

    private val logger = LoggerFactory.getLogger(GameDispatcher::class.java)

    override suspend fun dispatchRequests(message: WsRequest) {
        when (message) {
            is GameRequest -> wsService.emitFromSocket(message)
            else -> logger.error("Game Request not implemented for $message")
        }
    }
    override suspend fun dispatchResponse(message: WsResponse) {
        logger.error("Server should not receive Response  $message")
    }

    override suspend fun dispatchProtocol(message: WsProtocol) {
        wsService.emitFromSocket(message)
    }
}