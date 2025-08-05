package serializers

import dto.LogInUserDTO
import dto.UserCreationDTO
import dto.UserOUT
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object UserSerializers {
    fun List<UserOUT>.toJson() = Json.encodeToString(this)
    fun UserOUT.toJson() = Json.encodeToString(this)

    fun String.toUserIn() = Json.decodeFromString<UserCreationDTO>(this)
    fun String.toUserOut() = Json.decodeFromString<UserOUT>(this)


    fun UserCreationDTO.toJson() = Json.encodeToString(this)

    fun String.toLogInUser() = Json.decodeFromString<LogInUserDTO>(this)

}