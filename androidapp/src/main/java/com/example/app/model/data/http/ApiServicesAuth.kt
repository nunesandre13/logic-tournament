package com.example.app.model.data.http

import dto.LogInUserDTO
import dto.TokensDTO
import dto.UserAuthDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServicesAuth {

    @Headers("Content-Type: application/json")
    @POST("users/auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): TokensDTO

    @Headers("Content-Type: application/json")
    @POST("users/auth/refresh")
    fun refreshTokenSync(@Header("Authorization") token: String): Call<TokensDTO>

    @Headers("Content-Type: application/json")
    @PUT("users/auth")
    suspend fun login(@Body loginUser: LogInUserDTO): UserAuthDTO
}