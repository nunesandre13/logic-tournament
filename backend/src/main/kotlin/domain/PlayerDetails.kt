package org.example.domain

data class PlayerDetails(
    val id: Long,
    val name: String,
    val email: String,
    val rating: Int = 1200 // ELO inicial
)