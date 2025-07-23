package core

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Id,
    val name: String
)