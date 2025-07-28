package services

import model.Command
import model.Game
import model.GameType
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import model.Id
import services.dataStructures.GameRoom
import services.dataStructures.GameRoomsDataStructure

class GameRoomManager () {
    private val gameRooms : Map<GameType, GameRoomsDataStructure> = GameType.entries.associateWith { GameRoomsDataStructure() }

    fun createRoom(game: Game, id: Id): GameRoom {
        return gameRooms[game.gameType]?.createGameRoom(game,game.players, id) ?: throw IllegalStateException("GameType $game.gameType not found")
    }

    fun getGameRoom(gameType: GameType, id: Id): GameRoom {
        return gameRooms[gameType]?.getById(id) ?: throw IllegalStateException("GameType $gameType not found")
    }

    fun updateGameRoom(command: Command.PlayCommand, id: Id) {
        return runBlocking { gameRooms[command.gameType]?.getById(id)?.play(command) ?: throw IllegalStateException("GameType $command.gameType not found") }
    }

    fun collectGameChanges(gameType: GameType, id: Id): StateFlow<Game> {
        return gameRooms[gameType]?.getById(id)?.stateFlow ?: throw IllegalStateException("GameType $gameType not found")
    }

}