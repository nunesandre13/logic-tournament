package services.ServicesInterfaces

import domain.User

interface IUsersServices: UserLookUp {
    fun createUser(userName: String, email: String): User

    fun listUsers(): List<User>
}