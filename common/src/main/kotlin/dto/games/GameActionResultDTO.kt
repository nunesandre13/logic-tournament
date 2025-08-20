package dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
sealed class GameActionResultDTO : GameResponse {

    @Serializable
    @SerialName("Success")
    data class SuccessDTO(val game: GameDTO) : GameActionResultDTO()

    @Serializable
    @SerialName("InvalidMove")
    data class InvalidMoveDTO(val message: String) : GameActionResultDTO()

    @Serializable
    @SerialName("NotYourTurn")
    data class NotYourTurnDTO(val message: String) : GameActionResultDTO()

    @Serializable
    @SerialName("GameEnded")
    data class GameEndedDTO(val message: String, val game: GameDTO) : GameActionResultDTO()

    @Serializable
    @SerialName("InvalidCommand")
    data class InvalidCommandDTO(val message: String) : GameActionResultDTO()
}
