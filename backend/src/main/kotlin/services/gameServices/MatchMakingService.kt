package services.gameServices

import domain.games.GameType
import domain.Player
import domain.CancellationMatchMakingResult
import domain.MatchMakingResult
import domain.games.IGameFactory
import services.dataStructures.AwaitingPlayersSinc
import kotlin.time.Duration.Companion.minutes

class MatchMakingService(
    val gameRoomManager: GameRoomManager,
    private val gameFactory: IGameFactory
) {
    private val timeToAwait = 2.minutes
    private val waitingPlayers: Map<GameType, AwaitingPlayersSinc> = GameType.entries.associateWith { AwaitingPlayersSinc(timeToAwait) }

    fun match(gameType : GameType, player : Player) : MatchMakingResult {
       waitingPlayers[gameType]?.let { awaitingPlayersSinc ->
           when (val playerMatched = awaitingPlayersSinc.getPlayer(player)) {
               is AwaitingPlayersSinc.MatchingResult.CreatorSuccess -> {
                   val gameRoom = gameRoomManager.createRoom(gameFactory.createGame(gameType,
                       listOf(playerMatched.matchedPlayer, player)),playerMatched.roomId)
                   return MatchMakingResult.Success(gameRoom)
               }
               is AwaitingPlayersSinc.MatchingResult.AwaitedSuccess -> {
                   return MatchMakingResult.Success(gameRoomManager.getGameRoom(gameType, playerMatched.roomId))
               }
               is AwaitingPlayersSinc.MatchingResult.Failure -> return  MatchMakingResult.Failure(IllegalArgumentException("Player $player"))
           }

        } ?: return MatchMakingResult.Failure(IllegalStateException("Player $player"))
    }

    fun cancelMatch(gameType : GameType, player : Player) : CancellationMatchMakingResult {
        waitingPlayers[gameType]?.let { awaitingPlayersSinc ->
            return if (awaitingPlayersSinc.cancelMatchingPlayer(player)) CancellationMatchMakingResult.Cancelled else CancellationMatchMakingResult.ImpossibleToCancel
        } ?: return CancellationMatchMakingResult.ImpossibleToCancel
    }
}