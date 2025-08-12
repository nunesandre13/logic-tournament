package com.example.app.model.services

import com.example.app.model.data.http.interfaces.DataUsers
import domain.Tokens
import dto.LogInUserDTO
import dto.UserCreatedDTO
import dto.UserCreationDTO
import dto.UserOUT


class UserServices(val dataSource: DataUsers) {
    suspend fun getUserById(token: String, id: Int): UserOUT = dataSource.getUserById(token, id)

    suspend fun login(loginUser: LogInUserDTO): Tokens = dataSource.login(loginUser)

    suspend fun createUser(user: UserCreationDTO): UserCreatedDTO = dataSource.createUser(user)

    suspend fun refreshToken(token: String, user: UserCreationDTO): Tokens = dataSource.refreshToken(token, user)

}