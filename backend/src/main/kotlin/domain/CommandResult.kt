package domain

import domain.games.GameActionResult
import domain.games.GameType

sealed class CommandResult {
    // something to do here
    data class Success(val message: String) : CommandResult()
    data class Error(val message: String, val throwable: Throwable? = null) : CommandResult()
    data class MatchSucceed(val gameRoomId : Id, val gameType: GameType) : CommandResult()
    data object MatchError : CommandResult()
    data class ActionResult(val gameActionResult: GameActionResult) : CommandResult()
}