package serializers

import dto.WsCommunication
import serialization.AppJson
import kotlinx.serialization.encodeToString

object WebSocketResponseSerializer {
    fun WsCommunication.toJson(): String = AppJson.encodeToString<WsCommunication>(this)

    fun String.fromJson() = AppJson.decodeFromString<WsCommunication>(this)
}