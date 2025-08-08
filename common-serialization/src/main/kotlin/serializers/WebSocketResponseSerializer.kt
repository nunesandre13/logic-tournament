package serializers

import serialization.AppJson
import dto.WebSocketMessage
import kotlinx.serialization.encodeToString

object WebSocketResponseSerializer {
    fun WebSocketMessage.toJson(): String = AppJson.encodeToString<WebSocketMessage>(this)

    fun String.fromJson() = AppJson.decodeFromString<WebSocketMessage>(this)
}