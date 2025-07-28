package serializers

import dto.PlayerDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class PlayerSerializer {
    fun PlayerDTO.toJson(): String = Json.encodeToString(this)

    fun String.toPlayer() = Json.decodeFromString<PlayerDTO>(this)
}