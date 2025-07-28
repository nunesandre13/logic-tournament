package services

import domain.CancellationMatchMakingResult
import domain.CommandResult
import domain.MatchMakingResult
import kotlinx.coroutines.flow.StateFlow
import model.*


class GameService(
private val matchmakingService: MatchMakingService,
private val gameRoomManager: GameRoomManager,
) {

    fun getStateChange(roomId: Id, gameType: GameType): StateFlow<Game>{
        return gameRoomManager.collectGameChanges(gameType, roomId)
    }

    fun receiveCmd(command: GameCommands, roomId: Id? = null): CommandResult {
        return when (command) {
            is GameCommands.MatchingCommand -> treatMatchCommand(command)
            is GameCommands.PlayCommand -> treatPlayCommand(command,roomId)
        }
    }


    private fun treatMatchCommand(command: GameCommands.MatchingCommand): CommandResult {
        return when (command) {
            is GameCommands.MatchingCommand.RequestMatch -> requestMatch(command.player, command.gameType)
            is GameCommands.MatchingCommand.CancelMatchSearching -> cancelMatch(command.player, command.gameType)
        }
    }

    private fun requestMatch(player: Player, gameType: GameType): CommandResult {
        return when (val matchResponse = matchmakingService.match(gameType, player)) {
            is MatchMakingResult.Success -> CommandResult.MatchSucceed(matchResponse.gameRoom.id,gameType)
            is MatchMakingResult.Failure -> CommandResult.MatchError
        }
    }

    private fun cancelMatch(player: Player, gameType: GameType): CommandResult {
       val request = matchmakingService.cancelMatch(gameType, player)
        return when (request) {
            is CancellationMatchMakingResult.Cancelled -> CommandResult.Success("Cancelado com sucesso")
            is CancellationMatchMakingResult.ImpossibleToCancel -> CommandResult.Error("Cancelamento Nao sucesso")
        }
    }

    private fun treatPlayCommand(command: GameCommands.PlayCommand, roomId: Id?): CommandResult {
        val gameRoom = gameRoomManager.getGameRoom(command.gameType,roomId ?: throw IllegalStateException())
        try {
            gameRoomManager.updateGameRoom(command,gameRoom.id)
            return CommandResult.Success("Play " + command.player)
        }catch (e:Exception){
         return CommandResult.Error("Something")
        }
    }

}
