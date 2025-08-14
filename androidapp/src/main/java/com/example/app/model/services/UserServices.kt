package com.example.app.model.services

import com.example.app.model.data.http.interfaces.DataUsers
import domain.UserAuthResponse
import dto.UserCreationDTO
import dto.UserOUT


class UserServices(val dataSource: DataUsers) {
    suspend fun getUserById(token: String, id: Int): UserOUT = dataSource.getUserById(token, id)
    suspend fun createUser(user: UserCreationDTO): UserAuthResponse = dataSource.createUser(user)
}