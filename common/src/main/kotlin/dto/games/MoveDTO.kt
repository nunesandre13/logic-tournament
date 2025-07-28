package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class MoveDTO {
    @Serializable
    @SerialName("TicTacToeMove")
    data class TicTacToeMoveDTO(val row: Int, val col: Int) : MoveDTO()
}
