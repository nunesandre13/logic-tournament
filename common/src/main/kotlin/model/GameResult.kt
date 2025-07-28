package model

import kotlinx.serialization.Serializable

@Serializable
sealed class GameResult {
    data object Ongoing : GameResult()
    data object Draw : GameResult()
    data class Win(val winner: Player) : GameResult()
}