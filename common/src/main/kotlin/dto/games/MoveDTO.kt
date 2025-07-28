package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class MoveDTO {

    @Serializable
    @SerialName("CheckersMove")
    data class CheckersMoveDTO(val from: Int, val to: Int) : MoveDTO()
}
