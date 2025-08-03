package services.userService

import data.dataInterfaces.UserData
import domain.Id
import domain.User
import services.ServicesInterfaces.IUsersServices

class UserServices(private val userData: UserData): IUsersServices {

    override fun createUser(userName: String, email: String): User = userData.save(userName, email)

    override fun listUsers(): List<User> = userData.findAll()

    override fun getUserById(id: Id): User? = userData.findById(id.id)

}