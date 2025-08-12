package com.example.app.model.data.http

import com.example.app.model.data.http.interfaces.DataUsers
import domain.Tokens
import dto.LogInUserDTO
import dto.TokenDTO
import dto.UserCreatedDTO
import dto.UserCreationDTO
import dto.UserOUT
import toDomain

class DataSourceUsers(private val api: ApiServiceUsers): DataUsers{

    override suspend fun getUserById(token: String, id: Int): UserOUT = api.getUserById(token, id)

    override suspend fun login(loginUser: LogInUserDTO): Tokens = api.login(loginUser).toDomain()

    override suspend fun createUser(user: UserCreationDTO): UserCreatedDTO = api.createUser(user)

    override suspend fun refreshToken(token: String, user: UserCreationDTO): Tokens = api.refreshToken(token, user).toDomain()
}