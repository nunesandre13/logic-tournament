package dto

import kotlinx.serialization.Serializable
@Serializable
data class MessageRequest(val message: String): WsRequest