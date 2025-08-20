package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class GameResultDTO {
    @Serializable
    @SerialName("Ongoing")
    data object Ongoing : GameResultDTO()

    @Serializable
    @SerialName("Draw")
    data object Draw : GameResultDTO()

    @Serializable
    @SerialName("Win")
    data class Win(val winner: PlayerDTO) : GameResultDTO()

    @Serializable
    @SerialName("Quit")
    data object Quit : GameResultDTO()
}