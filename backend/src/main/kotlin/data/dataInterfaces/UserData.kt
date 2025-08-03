package data.dataInterfaces

import domain.User

interface UserData {
    fun findById(id: Long): User?

    fun save(userName: String, email: String): User

    fun findAll(): List<User>
}