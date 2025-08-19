package services.ServicesInterfaces

import domain.Id
import domain.User

interface UserLookUp {
    fun getUserById(id: Id): User?

    fun authenticate(email: String, password: String): User

    fun getUserByToken(token: String): User?
}
