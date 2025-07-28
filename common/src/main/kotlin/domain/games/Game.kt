package domain.games

import domain.Move
import domain.Player


interface Game {
    val gameType: GameType
    val players: List<Player>
    val result : GameResult
    val currentPlayer: Player
    fun play(command: GameCommands.PlayCommand): Game
    fun currentState(): GameState
    fun availableMoves(): List<Move>
}
