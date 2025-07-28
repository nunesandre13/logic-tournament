package domain

import model.Game

sealed class GameActionResult {

    data class Success(val game: Game) : GameActionResult()
    data class InvalidMove(val message: String) : GameActionResult()
    data class NotYourTurn(val message: String) : GameActionResult()
    data class GameEnded(val message: String) : GameActionResult()
}