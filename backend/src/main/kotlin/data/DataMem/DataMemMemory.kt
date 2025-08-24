package data.DataMem

import domain.Email
import domain.Id
import domain.RefreshToken
import domain.User
import java.util.concurrent.ConcurrentHashMap


object DataMemMemory {
    val UserTable = ConcurrentHashMap<Id, UserRow>()
    val authTable = ConcurrentHashMap<Id, RefreshToken>()
    data class UserRow(val user: User, val passwordHash: ByteArray)
}