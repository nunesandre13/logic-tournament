package data.DataMem

import data.DataMem.DataMemMemory.UserRow
import data.dataInterfaces.UserData
import domain.Email
import domain.Id
import domain.User
import domain.UsersWithCredentials
import kotlin.random.Random

class UserDataMem: UserData {
    private val users = DataMemMemory.UserTable

    override fun findById(id: Long): User? {
        val user = users[Id(id)] ?: return null
        return user.user
    }

    override fun save(userName: String, email: String, passwordHash: ByteArray): User {
        val id = Random.nextLong(1,1000000000)
        val user = User(Id(id), userName, Email(email))
        users[Id(id)] = UserRow(user,passwordHash)
        return user
    }

    override fun findAll(): List<User> = users.values.toList().map { it.user }

    override fun getUserPassWordHash(email: Email): UsersWithCredentials? = users.values.firstOrNull{ it.user.email == email }.let {
        UsersWithCredentials(it?.user ?: return null, it.passwordHash) }

}