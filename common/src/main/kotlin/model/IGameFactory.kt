package model

interface IGameFactory {
    fun createGame(gameType: GameType, players: List<Player>): Game
}