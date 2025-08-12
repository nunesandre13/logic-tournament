package com.example.app.model.data.http.interfaces

import android.media.session.MediaSession.Token
import domain.Tokens
import dto.LogInUserDTO
import dto.TokenDTO
import dto.UserCreatedDTO
import dto.UserCreationDTO
import dto.UserOUT

interface DataUsers {
    suspend fun getUserById(token: String, id: Int): UserOUT

    suspend fun login(loginUser: LogInUserDTO): Tokens

    suspend fun createUser(user: UserCreationDTO): UserCreatedDTO

    suspend fun refreshToken(token: String, user: UserCreationDTO): Tokens
}