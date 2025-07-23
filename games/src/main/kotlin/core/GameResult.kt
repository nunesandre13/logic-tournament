package core

sealed class GameResult {
    object Ongoing : GameResult()
    object Draw : GameResult()
    data class Win(val winner: Player) : GameResult()
}