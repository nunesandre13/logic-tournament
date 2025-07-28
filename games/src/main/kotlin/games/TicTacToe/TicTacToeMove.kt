package games.TicTacToe

import domain.Move
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable // Anote a classe de dados concreta
@SerialName("TIC_TAC_TOE_MOVE")
data class TicTacToeMove(val row: Int, val col: Int) : Move