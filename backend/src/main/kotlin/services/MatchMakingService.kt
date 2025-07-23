package services

import core.GameType
import core.Player
import domain.GameRoom
import services.dataStructures.AwaitingPlayersSinc
import kotlin.time.Duration.Companion.minutes

class MatchMakingService(
    private val gameRoomManager: GameRoomManager, // Injetamos o GameRoomManager
    private val gameFactory: GameFactory
) {
    private val timeToAwait = 1.minutes
    private val waitingPlayers: Map<GameType, AwaitingPlayersSinc> = GameType.entries.associateWith { AwaitingPlayersSinc(timeToAwait) }

    fun match(gameType : GameType, player : Player) : GameRoom {
       waitingPlayers[gameType]?.let { awaitingPlayersSinc ->
           when (val playerMatched = awaitingPlayersSinc.getPlayer(player)) {
               is AwaitingPlayersSinc.MatchingResult.CreatorSuccess -> {
                   val gameRoom = gameRoomManager.createRoom(gameFactory.createGame(gameType,
                       listOf(playerMatched.matchedPlayer, player)),playerMatched.roomId)
                   return gameRoom
               }
               is AwaitingPlayersSinc.MatchingResult.AwaitedSuccess -> {
                   return gameRoomManager.getGameRoom(gameType, playerMatched.roomId)
               }
               is AwaitingPlayersSinc.MatchingResult.Failure -> throw  IllegalArgumentException("Player $player")
           }

        } ?: throw  IllegalArgumentException("Player $player")
    }

    // boolean por agora np futuro uma mensagem de erro
    fun cancelMatch(gameType : GameType, player : Player) : Boolean {
        waitingPlayers[gameType]?.let { awaitingPlayersSinc ->
            return awaitingPlayersSinc.cancelMatchingPlayer(player)
        } ?: return false
    }
}