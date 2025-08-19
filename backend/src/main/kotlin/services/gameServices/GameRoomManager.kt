package services.gameServices

import domain.games.Game
import domain.games.GameType
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import domain.Id
import domain.Player
import domain.games.GameActionResult
import domain.games.GameCommands
import services.dataStructures.GameRoom
import services.dataStructures.GameRoomsDataStructure

class GameRoomManager {
    private val gameRooms : Map<GameType, GameRoomsDataStructure> = GameType.entries.associateWith { GameRoomsDataStructure() }

    fun createRoom(game: Game, id: Id): GameRoom {
        return gameRooms[game.gameType]?.createGameRoom(game,game.players, id) ?: throw IllegalStateException("GameType $game.gameType not found")
    }

    fun getGameRoom(gameType: GameType, id: Id): GameRoom {
        return gameRooms[gameType]?.getById(id) ?: throw IllegalStateException("GameType $gameType not found")
    }

    fun updateGameRoom(command: GameCommands.PlayCommand, id: Id): GameActionResult = runBlocking {
            val gameRoom = gameRooms[command.gameType]?.getById(id) ?: throw IllegalStateException("GameType $command.gameType not found")
            verifyPlayer(command.player,gameRoom).apply {
                if (!this) throw IllegalStateException("")
            }
            return@runBlocking gameRoom.play(command)
        }

    fun deleteGameRoom(gameType: GameType, id: Id): Boolean{
        gameRooms[gameType]?.destroy(id) ?: return false
        return true
    }
    private fun verifyPlayer(player: Player, gameRoom: GameRoom) = gameRoom.players.any { it == player }

    fun collectGameChanges(gameType: GameType, id: Id): StateFlow<Game> {
        return gameRooms[gameType]?.getById(id)?.stateFlow ?: throw IllegalStateException("GameType $gameType not found")
    }

}