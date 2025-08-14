package com.example.app.model.data.http.interfaces

import domain.UserAuthResponse
import dto.UserCreationDTO
import dto.UserOUT

interface DataUsers {
    suspend fun getUserById(token: String, id: Int): UserOUT

    suspend fun createUser(user: UserCreationDTO): UserAuthResponse
}