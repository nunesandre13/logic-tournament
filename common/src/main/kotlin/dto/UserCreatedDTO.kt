package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCreatedDTO(
    val user: UserOUT,
    val tokenDTO: TokenDTO
)