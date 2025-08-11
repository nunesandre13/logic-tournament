package com.example.app.model.data.http

import dto.LogInUserDTO
import dto.TokenDTO
import dto.UserCreatedDTO
import dto.UserCreationDTO
import dto.UserOUT

class DataSourceUsers(private val api: ApiServiceUsers){

    suspend fun getUserById(token: String, id: Int): UserOUT = api.getUserById(token, id)

    suspend fun login(loginUser: LogInUserDTO): TokenDTO = api.login(loginUser)

    suspend fun createUser(user: UserCreationDTO): UserCreatedDTO = api.createUser(user)

    suspend fun refreshToken(token: String, user: UserCreationDTO): TokenDTO = api.refreshToken(token, user)
}