package dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO(val accessToken: String, val refreshToken: String)