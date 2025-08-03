package serializers

import dto.UserIN
import dto.UserOUT
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object UserSerializers {
    fun List<UserOUT>.toJson() = Json.encodeToString(this)
    fun UserOUT.toJson() = Json.encodeToString(this)

    fun String.toUserIn() = Json.decodeFromString<UserIN>(this)
    fun String.toUserOut() = Json.decodeFromString<UserOUT>(this)


    fun UserIN.toJson() = Json.encodeToString(this)

}