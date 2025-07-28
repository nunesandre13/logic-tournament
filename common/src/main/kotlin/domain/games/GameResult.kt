package domain.games

import domain.Player

sealed class GameResult {
    data object Ongoing : GameResult()
    data object Draw : GameResult()
    data class Win(val winner: Player) : GameResult()
}