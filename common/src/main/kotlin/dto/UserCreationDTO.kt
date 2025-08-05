package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCreationDTO (val name: String, val email: String, val password: String)