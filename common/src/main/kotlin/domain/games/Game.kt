package domain.games

import domain.Player


interface Game: GameEvent {
    val gameType: GameType
    val players: List<Player>
    val result : GameResult
    val currentPlayer: Player
    fun play(command: GameCommands.PlayCommand): GameActionResult
    fun currentState(): GameState
    fun availableMoves(): List<Move>
}
