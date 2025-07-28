package dto

import kotlinx.serialization.Serializable
import model.Id

@Serializable
data class PlayerDTO(val id: Id, val name: String)