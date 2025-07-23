package services

import core.Command
import core.*
import domain.CancellationMatchMakingResult
import domain.CommandResult
import domain.MatchMakingResult


class GameService(
private val matchmakingService: MatchMakingService,
private val gameRoomManager: GameRoomManager,
) {

    fun receiveCmd(command: Command): CommandResult {
        return when (command) {
            is Command.MatchingCommand -> treatMatchCommand(command)
            is Command.PlayCommand -> treatPlayCommand(command)
        }
    }


    private fun treatMatchCommand(command: Command.MatchingCommand): CommandResult {
        return when (command) {
            is Command.MatchingCommand.RequestMatch -> requestMatch(command.player, command.gameType)
            is Command.MatchingCommand.CancelMatchSearching -> cancelMatch(command.player, command.gameType)
        }
    }

    private fun requestMatch(player: Player, gameType: GameType ): CommandResult {
        return when (val matchResponse = matchmakingService.match(gameType, player)) {
            is MatchMakingResult.Success -> CommandResult.MatchSucceed(matchResponse.gameRoom)
            is MatchMakingResult.Failure -> CommandResult.MatchError
        }
    }

    private fun cancelMatch(player: Player, gameType: GameType ): CommandResult {
       val request = matchmakingService.cancelMatch(gameType, player)
        return when (request) {
            is CancellationMatchMakingResult.Cancelled -> CommandResult.Success("Cancelado com sucesso")
            is CancellationMatchMakingResult.ImpossibleToCancel -> CommandResult.Error("Cancelamento Nao sucesso")
        }
    }

    private fun treatPlayCommand(command: Command.PlayCommand): CommandResult {
        val gameRoom = gameRoomManager.getGameRoom(command.gameType,command.roomId!!)
        try {
            val play = gameRoom.game.play(command)
            gameRoomManager.updateGameRoom(play,gameRoom.id)
            return CommandResult.Success("Play " + command.player)
        }catch (e:Exception){
         return CommandResult.Error("Something")
        }
    }

}
