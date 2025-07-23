package services

import core.Game
import core.GameType
import domain.GameRoom
import org.example.commonDomain.Id
import services.dataStructures.GameRoomsDataStructure

class GameRoomManager () {
    private val gameRooms : Map<GameType, GameRoomsDataStructure> = GameType.entries.associateWith { GameRoomsDataStructure() }

    fun createRoom(game: Game, id: Id): GameRoom {
        return gameRooms[game.type]?.createGameRoom(game,game.players, id) ?: throw IllegalStateException("GameType $game.gameType not found")
    }

    fun getGameRoom(gameType: GameType, id: Id): GameRoom {
        return gameRooms[gameType]?.getById(id) ?: throw IllegalStateException("GameType $gameType not found")
    }

    fun updateGameRoom(game: Game, id: Id) {
        gameRooms[game.type]?.updateGameInstance(id,game) ?: throw IllegalStateException("GameType $game.gameType not found")
    }

}