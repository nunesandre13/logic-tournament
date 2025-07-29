package dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDetailsIN (val playerName: String, val email: String)