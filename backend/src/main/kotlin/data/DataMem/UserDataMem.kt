package data.DataMem

import data.dataInterfaces.UserData
import domain.Email
import domain.Id
import domain.User
import kotlin.random.Random

class UserDataMem: UserData {
    private val users = mutableMapOf<Long, Pair<User,ByteArray>>()

    override fun findById(id: Long): User? = users[id]?.first

    override fun save(userName: String, email: String, passwordHash: ByteArray): User {
        val id = Random.nextLong(1,1000000000)
        val user = User(Id(id), userName, Email(email))
        users[id] = user to passwordHash
        return user
    }

    override fun findAll(): List<User> = users.values.toList().map { it.first }

    override fun getUserPassWordHash(email: Email): ByteArray? = users.values.firstOrNull{ it.first.email == email }?.second
}