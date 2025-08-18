package dto

import domain.games.GameType
import kotlinx.serialization.Serializable

@Serializable
sealed class MatchResultDTO: GameResponse {
    @Serializable
    data class SuccessDTO(val roomID: IdDTO) : MatchResultDTO()

    @Serializable
    data class FailureDTO(val error: String) : MatchResultDTO()
}