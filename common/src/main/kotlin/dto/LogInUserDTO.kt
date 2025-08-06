package dto

import kotlinx.serialization.Serializable

@Serializable
data class LogInUserDTO(val email: String, val password: String)
