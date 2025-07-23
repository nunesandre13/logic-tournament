package core

import kotlinx.serialization.Serializable
import org.example.commonDomain.Id

@Serializable
data class Player(
    val id: Id,
    val name: String
)