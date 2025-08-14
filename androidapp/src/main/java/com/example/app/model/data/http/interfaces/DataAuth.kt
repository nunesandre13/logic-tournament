package com.example.app.model.data.http.interfaces

import domain.Tokens
import domain.UserAuthResponse
import dto.LogInUserDTO

interface DataAuth {
    suspend fun refreshToken(token: String): Tokens

    fun refreshTokenSync(refreshToken: String): Tokens

    suspend fun login(loginUser: LogInUserDTO): UserAuthResponse

}