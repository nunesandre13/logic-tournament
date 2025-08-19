package data.DataMem

import data.DataMem.DataMemMemory.UserRow
import data.dataInterfaces.UserData
import domain.Email
import domain.Id
import domain.User
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

    override fun getUserPassWordHash(email: Email): Pair<User,ByteArray>? = users.values.firstOrNull{ it.user.email == email }.let {
        Pair(it?.user ?: return null, it.passwordHash) }

    override fun findByToken(token: String): User? {
       val email =  DataMemMemory.authTable.entries.firstOrNull{
            it.value.token == token
        }?.key
        print(email)
        email ?: return null
        return DataMemMemory.UserTable.values.firstOrNull { it.user.email == email }?.user
    }
}