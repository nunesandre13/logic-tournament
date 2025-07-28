package domain.games

import domain.Player

interface IGameFactory {
    fun createGame(gameType: GameType, players: List<Player>): Game
}