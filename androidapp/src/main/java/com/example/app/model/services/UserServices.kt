package com.example.app.model.services

import com.example.app.model.data.http.interfaces.DataUsers
import domain.User
import domain.UserAuthResponse
import dto.UserCreationDTO
import dto.UserOUT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class UserServices(val dataSource: DataUsers) {

    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> = _loggedUser

    suspend fun getUserByToken(): User?  {
        val user = dataSource.getUserInfo()
        user?.also {
            _loggedUser.emit(it)
        }
        return user
    }

    suspend fun getUserById(token: String, id: Int): UserOUT = dataSource.getUserById(token, id)

    suspend fun createUser(user: UserCreationDTO): UserAuthResponse = dataSource.createUser(user).let {  user ->
        _loggedUser.emit(user.user)
        user
    }
}