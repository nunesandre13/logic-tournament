package com.example.app.model.data.http

import com.example.app.model.data.http.interfaces.DataAuth
import domain.Tokens
import domain.UserAuthResponse
import dto.LogInUserDTO
import toDomain

class DataSourceAuth(private val api: ApiServicesAuth): DataAuth {

    override suspend fun login(loginUser: LogInUserDTO): UserAuthResponse = api.login(loginUser).toDomain()

    override suspend fun refreshToken(token: String): Tokens = api.refreshToken(token).toDomain()

    override fun refreshTokenSync(refreshToken: String): Tokens  = api.refreshTokenSync(refreshToken).execute().body()?.toDomain()
        ?: throw Exception("Erro ao renovar token")

}