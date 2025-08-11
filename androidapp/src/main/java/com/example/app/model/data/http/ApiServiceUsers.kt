package com.example.app.model.data.http

import dto.LogInUserDTO
import dto.TokenDTO
import dto.UserCreatedDTO
import dto.UserCreationDTO
import dto.UserOUT
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceUsers {

    @Headers("Content-Type: application/json")
    @GET("users/{id}")
    suspend fun getUserById(@Header("Authorization") token: String, @Path("id") id: Int): UserOUT

    @Headers("Content-Type: application/json")
    @PUT("users/auth")
    suspend fun login(@Body loginUser: LogInUserDTO): TokenDTO

    @Headers("Content-Type: application/json")
    @POST
    suspend fun createUser(@Body user: UserCreationDTO): UserCreatedDTO

    @Headers("Content-Type: application/json")
    @POST("users/auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String, @Body user: UserCreationDTO): TokenDTO

}