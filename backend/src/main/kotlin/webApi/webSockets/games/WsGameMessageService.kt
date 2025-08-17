package webApi.webSockets.games

import WebSocketChannel
import WebSocketMessageService
import domain.CommandResult
import domain.games.Game
import dto.ConnectionClosed
import dto.ConnectionOpened
import dto.GameCommandsDTO
import dto.GameRequest
import dto.GameResponse
import dto.GamesMessage
import dto.HeartBeat
import dto.MatchResultDTO
import dto.WsProtocol
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mappers.IGameMappers
import org.slf4j.LoggerFactory
import services.ServicesInterfaces.IGameServices
import toDTO
import toDomain

class WsGameMessageService(private val gamesService: IGameServices, private val socketChannel: WebSocketChannel<GameResponse, GameRequest, WsProtocol>, val mappers: IGameMappers): WebSocketMessageService<WsProtocol, GameRequest, GameResponse >{

    private val logger = LoggerFactory.getLogger(WsGameMessageService::class.java)

    override suspend fun onResponse(message: GameResponse) {
        socketChannel.sendToSocket(message)
    }

    override suspend fun onRequest(message: GameRequest) {
        when (message) {
            is GameCommandsDTO -> handleCommandDTO(message)
        }
    }

    override suspend fun onProtocol(message: WsProtocol) {
        when (message) {
            is HeartBeat -> logger.info("HEARTBEAT: ${message.timestamp}")
            is ConnectionClosed -> logger.info("Connection closed")
            is ConnectionOpened -> logger.info("Connection opened")
        }
    }

    private suspend fun handleCommandDTO(gameCommand: GameCommandsDTO){
        val result = when (gameCommand) {
            is GameCommandsDTO.PlayCommandDTO -> {
                gamesService.receiveCmd(mappers.toPlayCommandDomain(gameCommand), gameCommand.roomId?.toDomain())
            }
            is GameCommandsDTO.MatchingCommandDTO -> {
                gamesService.receiveCmd(mappers.toMatchCommandDomain(gameCommand), null)
            }
        }
        when (result) {
            is CommandResult.ActionResult -> {
                onResponse(mappers.toDTO(result.gameActionResult))
            }
            is CommandResult.Error -> {
                onResponse(GamesMessage("Something went wrong"))
            }

            is CommandResult.MatchError -> {
                onResponse(GamesMessage("Something went wrong"))
            }

            is CommandResult.MatchSucceed -> {
                onResponse(MatchResultDTO.SuccessDTO(result.gameRoomId.toDTO()))
                val gameFlow = gamesService.getStateChange(result.gameRoomId, result.gameType)
                startSendingGameState(gameFlow)
            }
            is CommandResult.Success -> {
                onResponse(GamesMessage("Success!"))
            }
        }
    }

    private suspend fun startSendingGameState(gameFlow: StateFlow<Game>)  = coroutineScope {
        launch {
            gameFlow.collect {
                onResponse(mappers.toDTO(it))
            }
        }
    }

}