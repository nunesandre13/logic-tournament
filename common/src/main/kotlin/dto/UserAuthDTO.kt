package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthDTO(
    val user: UserOUT,
    val tokensDTO: TokensDTO
)