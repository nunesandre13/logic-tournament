package domain.games

sealed class GameActionResult: GameData {
    data class Success(val game: Game) : GameActionResult()
    data class InvalidMove(val message: String) : GameActionResult()
    data class NotYourTurn(val message: String) : GameActionResult()
    data class GameEnded(val message: String) : GameActionResult()
    data class InvalidCommand(val message: String) : GameActionResult()
}