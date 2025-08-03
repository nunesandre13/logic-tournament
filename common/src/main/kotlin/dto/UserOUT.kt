package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserOUT(val name: String, val id: IdDTO, val email: String)