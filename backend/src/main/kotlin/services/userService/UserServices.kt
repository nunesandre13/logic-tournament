package services.userService

import data.dataInterfaces.UserData
import domain.Email
import domain.Id
import domain.User
import services.ServicesInterfaces.IUsersServices
import services.convertToHash
import services.verifyHashEquals

class UserServices(private val userData: UserData): IUsersServices {

    override fun createUser(userName: String, email: Email, password: String): User = userData.save(userName, email.email, convertToHash(email, password))

    override fun listUsers(): List<User> = userData.findAll()

    override fun getUserById(id: Id): User? = userData.findById(id.id)

    override fun authenticate(email: String, password: String): Boolean {
        val hash = userData.getUserPassWordHash(Email(email))  ?: throw IllegalStateException("Email does not exist")
        return verifyHashEquals(Email(email),password,hash)
    }

}