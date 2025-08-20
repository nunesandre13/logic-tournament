package webApi.webSockets.games

import domain.CommandResult
import dto.ConnectionClosed
import dto.ConnectionOpened
import dto.GameCommandsDTO
import dto.GameDTO
import dto.GameRequest
import dto.GameResponse
import dto.GamesMessage
import dto.HeartBeat
import dto.MatchResultDTO
import dto.WsProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import mappers.IGameMappers
import org.slf4j.LoggerFactory
import services.ServicesInterfaces.IGameServices
import toDTO
import toDomain
import kotlin.coroutines.coroutineContext

class WsServerGameHandlers(private val gamesService: IGameServices, val mappers: IGameMappers){

    private val logger = LoggerFactory.getLogger(WsServerGameHandlers::class.java)

    private val afterResponseChannel = Channel<suspend () -> Unit>(Channel.UNLIMITED)

    suspend fun afterResponse(task: suspend () -> Unit) {
        afterResponseChannel.send(task)
    }

    suspend fun flushAfterResponse() {
        while (true) {
            val task = afterResponseChannel.tryReceive().getOrNull() ?: break
            logger.info(task.toString())
            CoroutineScope(coroutineContext).launch {
                task()
            }
        }
    }

    suspend fun onRequest(message: GameRequest):GameResponse {
        return when (message) {
            is GameCommandsDTO -> handleCommandDTO(message)
        }
    }

    private val sharedGame = MutableSharedFlow<GameDTO>(replay = 1, extraBufferCapacity = 5 )
    val gameState: SharedFlow<GameDTO> = sharedGame

    suspend fun onProtocol(message: WsProtocol) {
        when (message) {
            is HeartBeat -> logger.info("HEARTBEAT: ${message.timestamp}")
            is ConnectionClosed -> logger.info("Connection closed")
            is ConnectionOpened -> logger.info("Connection opened")
        }
    }

    private suspend fun handleCommandDTO(gameCommand: GameCommandsDTO): GameResponse {
        val result = when (gameCommand) {
            is GameCommandsDTO.PlayCommandDTO -> {
                gamesService.receiveCmd(mappers.toPlayCommandDomain(gameCommand), gameCommand.roomId?.toDomain())
            }
            is GameCommandsDTO.MatchingCommandDTO -> {
                gamesService.receiveCmd(mappers.toMatchCommandDomain(gameCommand), null)
            }
        }
        return when (result) {
            is CommandResult.ActionResult -> {
                mappers.toDTO(result.gameActionResult)
            }
            is CommandResult.Error -> {
                GamesMessage("Something went wrong")
            }
            is CommandResult.MatchError -> {
                GamesMessage("Something went wrong")
            }
            is CommandResult.MatchSucceed -> {
                afterResponse {
                    gamesService.getStateChange(result.gameRoomId, result.gameType).collect { game ->
                        logger.info("collecting game -> $game")
                        val dto = mappers.toDTO(game)
                        sharedGame.emit(dto)
                    }
                }
                print("emitting the match successfully")
                MatchResultDTO.SuccessDTO(result.gameRoomId.toDTO())
            }
            is CommandResult.Success -> {
                GamesMessage("Success!")
            }
        }
    }
}