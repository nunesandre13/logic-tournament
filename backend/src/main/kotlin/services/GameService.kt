package services

import Command
import core.*
import domain.GameRoom
import domain.Id


class GameService(
private val matchmakingService: MatchMakingService,
private val gameRoomManager: GameRoomManager,
) {
    fun requestMatch(player: Player, gameType: GameType): MatchmakingResult {
        val result = matchmakingService.match(gameType, player)
        return MatchmakingResult(result)
    }

    fun receiveCmd(command: Command): CommandResult { // CommandResult é um tipo de retorno genérico para o handler
        when (command) {
            is Command.MakeMove -> {}
            else -> {}
        }
        return TODO()
    }



    sealed class CommandResult {
        data class Success(val message: String) : CommandResult()
        data class Error(val message: String, val throwable: Throwable? = null) : CommandResult()
        data class Data(val data: Any) : CommandResult() // Para comandos que retornam dados, como GetGameStatus
    }

}

// para pensar melhor ainda por agora nao, mas provavelmente um objeto de dominio
class MatchmakingResult (gameRoom : GameRoom) {}

// Exemplos de classes de resultado para makeMove
sealed class GameActionResult {
    data class Success(val message: String) : GameActionResult()
    data class InvalidMove(val message: String) : GameActionResult()
    data class NotYourTurn(val message: String) : GameActionResult()
    data class GameEnded(val result: Any) : GameActionResult() // O 'result' seria o objeto de resultado final do jogo
    // ...
}