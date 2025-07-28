package dto

import kotlinx.serialization.Serializable

@Serializable
sealed class MatchResultDTO: Event {
    @Serializable
    data class SuccessDTO(val roomID: IdDTO) : MatchResultDTO()

    @Serializable
    data class FailureDTO(val error: String) : MatchResultDTO()
}