package services.ServicesInterfaces

import domain.Id
import domain.User

interface UserLookUp {
    fun getUserById(id: Id): User?
}
