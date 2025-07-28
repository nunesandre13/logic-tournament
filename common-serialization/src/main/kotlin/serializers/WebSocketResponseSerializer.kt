package serializers

import AppJson
import dto.WebSocketResponse
import kotlinx.serialization.encodeToString

object WebSocketResponseSerializer {
    fun WebSocketResponse.toJson(): String = AppJson.encodeToString<WebSocketResponse>(this)

    fun String.fromJson() = AppJson.decodeFromString<WebSocketResponse>(this)
}