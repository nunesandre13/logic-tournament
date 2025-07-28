package domain

import domain.games.Game

data class Tournament(
    val id: String,
    val name: String,
    val participants: List<PlayerDetails>,
    val games: List<Game>
)