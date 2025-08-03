package serializers

import dto.IdDTO
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object CommonSerializers {
    fun String.toIdDTO() = Json.decodeFromString<IdDTO>(this)
    fun IdDTO.toJson() = Json.encodeToString(this)
}