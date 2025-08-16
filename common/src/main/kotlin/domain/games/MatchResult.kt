package domain.games

import domain.Id

sealed class MatchResult : GameEvent{
    data class Match(val roomId: Id) : MatchResult()
    data class InvalidMatch(val reason: String) : MatchResult()
}