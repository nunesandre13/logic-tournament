package dto

import kotlinx.serialization.Serializable

@Serializable
data class GamesMessage(val message : String): GameResponse