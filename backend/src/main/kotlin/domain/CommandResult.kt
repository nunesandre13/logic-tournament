package domain

import core.GameType
import org.example.commonDomain.Id

sealed class CommandResult {
    data class Success(val message: String) : CommandResult()
    data class Error(val message: String, val throwable: Throwable? = null) : CommandResult()
    data class MatchSucceed(val gameRoomId : Id, val gameType: GameType) : CommandResult()
    data object MatchError : CommandResult()
    data class ActionResult(val gameActionResult: GameActionResult) : CommandResult()
}