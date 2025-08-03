package services.ServicesInterfaces

import domain.CommandResult
import domain.Id
import domain.games.Game
import domain.games.GameCommands
import domain.games.GameType
import kotlinx.coroutines.flow.StateFlow

interface IGameServices {
    fun listAllGames(): List<GameType>

    fun getStateChange(roomId: Id, gameType: GameType): StateFlow<Game>

    fun receiveCmd(command: GameCommands, roomId: Id? = null): CommandResult
}