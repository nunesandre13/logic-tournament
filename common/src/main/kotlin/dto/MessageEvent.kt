package dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageEvent(val message: String): Event