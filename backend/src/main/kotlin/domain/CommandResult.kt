package domain

import services.dataStructures.AwaitingPlayersSinc

sealed class CommandResult {
    data class Success(val message: String) : CommandResult()
    data class Error(val message: String, val throwable: Throwable? = null) : CommandResult()
    data class MatchSucceed(val gameRoom : GameRoom) : CommandResult()
    data object MatchError : CommandResult()
    data class ActionResult(val gameActionResult: GameActionResult) : CommandResult()
}