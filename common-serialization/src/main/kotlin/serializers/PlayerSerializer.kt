package serializers

import dto.PlayerDTO
import dto.PlayerDetailsIN
import dto.PlayerDetailsOUT
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object PlayerSerializer {
    fun PlayerDTO.toJson(): String = Json.encodeToString(this)

    fun String.toPlayer() = Json.decodeFromString<PlayerDTO>(this)

    fun String.toPlayerDetailsIn() = Json.decodeFromString<PlayerDetailsIN>(this)

    fun PlayerDetailsOUT.playerDetailsOut() = Json.encodeToString(this)

}