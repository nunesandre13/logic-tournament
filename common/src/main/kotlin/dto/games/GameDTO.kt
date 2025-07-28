package dto

import kotlinx.serialization.Serializable

@Serializable
sealed class GameDTO: Event{
    @Serializable
    data class TicTacToeGameDTO(val players: List<PlayerDTO>, val board: List<List<Char>>, val currentPlayer: PlayerDTO, val result: GameResultDTO) : GameDTO()
}