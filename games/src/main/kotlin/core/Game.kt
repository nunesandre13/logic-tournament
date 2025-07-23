package core

interface Game {
    val type: GameType
    val players: List<Player>
    val result : GameResult
    val currentPlayer: Player
    fun play(command: Command.PlayCommand): Game
    fun currentState(): GameState
    fun availableMoves(): List<Move>
}
