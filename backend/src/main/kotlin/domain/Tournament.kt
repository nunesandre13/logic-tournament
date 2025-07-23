package org.example.domain

import core.Game

data class Tournament(
    val id: String,
    val name: String,
    val participants: List<PlayerDetails>,
    val games: List<Game>
)