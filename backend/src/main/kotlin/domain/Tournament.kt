package org.example.domain

import model.Game

data class Tournament(
    val id: String,
    val name: String,
    val participants: List<PlayerDetails>,
    val games: List<Game>
)