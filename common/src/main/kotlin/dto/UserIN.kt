package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserIN (val name: String, val email: String)