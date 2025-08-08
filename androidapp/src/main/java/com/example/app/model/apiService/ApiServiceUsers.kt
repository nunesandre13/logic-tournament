package com.example.app.model.apiService

import dto.LogInUserDTO
import dto.TokenDTO
import dto.UserCreatedDTO
import dto.UserCreationDTO
import dto.UserOUT
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceUsers {

    @GET("users/{id}")
    suspend fun getUserById(@Header("Authorization") token: String, @Path("id") id: Int): UserOUT

    @PUT("users/auth")
    suspend fun login(@Body loginUser: LogInUserDTO): TokenDTO

    @POST
    suspend fun createUser(@Body user: UserCreationDTO): UserCreatedDTO

    @POST("users/auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String, @Body user: UserCreationDTO): TokenDTO

}