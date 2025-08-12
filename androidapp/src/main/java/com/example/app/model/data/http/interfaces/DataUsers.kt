package com.example.app.model.data.http.interfaces

import domain.Tokens
import domain.UserAuthResponse
import dto.LogInUserDTO
import dto.UserAuthDTO
import dto.UserCreationDTO
import dto.UserOUT

interface DataUsers {
    suspend fun getUserById(token: String, id: Int): UserOUT

    suspend fun login(loginUser: LogInUserDTO): UserAuthResponse

    suspend fun createUser(user: UserCreationDTO): UserAuthResponse

    suspend fun refreshToken(token: String, user: UserCreationDTO): Tokens
}