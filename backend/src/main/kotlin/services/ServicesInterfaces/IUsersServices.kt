package services.ServicesInterfaces

import domain.Email
import domain.User

interface IUsersServices: UserLookUp {
    fun createUser(userName: String, email: Email, password: String): User

    fun listUsers(): List<User>
}