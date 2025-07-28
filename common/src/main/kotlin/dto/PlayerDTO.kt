package dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(val id: IdDTO, val name: String)