package data.DataMem

import data.dataInterfaces.UserData
import domain.Email
import domain.Id
import domain.User
import kotlin.random.Random

class UserDataMem: UserData {
    private val players = mutableMapOf<Long, User>()

    override fun findById(id: Long): User? = players[id]

    override fun save(userName: String, email: String): User {
        val id = Random.nextLong()
        val user = User(Id(id), userName, Email(email))
        players[id] = user
        return user
    }

    override fun findAll(): List<User> = players.values.toList()
}