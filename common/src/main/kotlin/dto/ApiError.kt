package dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int,        // código aplicacional
    val error: String,    // identificador textual
    val message: String   // mensagem amigável
)
