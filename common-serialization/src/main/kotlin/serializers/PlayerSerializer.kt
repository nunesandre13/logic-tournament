package serializers

import dto.PlayerDTO
import dto.UserIN
import dto.UserOUT
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object PlayerSerializer {
    fun List<PlayerDTO>.toJson() = Json.encodeToString(this)

    fun PlayerDTO.toJson(): String = Json.encodeToString(this)

    fun String.toPlayer() = Json.decodeFromString<PlayerDTO>(this)

    fun String.toPlayerDetailsIn() = Json.decodeFromString<UserIN>(this)
}