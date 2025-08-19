package com.example.app.model.data.http

import android.util.Log
import com.example.app.model.data.http.interfaces.DataUsers
import domain.Tokens
import domain.User
import domain.UserAuthResponse
import dto.LogInUserDTO
import dto.UserCreationDTO
import dto.UserOUT
import toDomain

class DataSourceUsers(private val api: ApiServiceUsers): DataUsers{

    override suspend fun getUserByToken(token: String): User {

        val c = api.getUserByToken(token).toDomain()
        Log.d("API", c.toString())
        return c
    }

    override suspend fun getUserById(token: String, id: Int): UserOUT = api.getUserById(token, id)
    override suspend fun createUser(user: UserCreationDTO): UserAuthResponse = api.createUser(user).toDomain()


}