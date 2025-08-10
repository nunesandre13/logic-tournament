package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class GameDTO: GameEventDTO{
    @Serializable
    @SerialName("TicTacToeDTO")
    data class TicTacToeGameDTO(val players: List<PlayerDTO>, val board: List<List<Char>>, val currentPlayer: PlayerDTO, val result: GameResultDTO) : GameDTO()
}