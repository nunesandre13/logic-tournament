package data.dataInterfaces

import domain.Email
import domain.User

interface UserData {
    fun findById(id: Long): User?

    fun save(userName: String, email: String, passwordHash: ByteArray): User

    fun findAll(): List<User>

    fun getUserPassWordHash(email: Email): ByteArray?
}