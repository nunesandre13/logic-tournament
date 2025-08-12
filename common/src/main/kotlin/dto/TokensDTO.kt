package dto

import kotlinx.serialization.Serializable

@Serializable
data class TokensDTO(val accessToken: String, val refreshToken: String)