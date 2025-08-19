package com.example.app.model.data.http

import dto.UserAuthDTO
import dto.UserCreationDTO
import dto.UserOUT
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceUsers {

    @Headers("Content-Type: application/json")
    @GET("users/me")
    suspend fun getUserByToken(@Header("Authorization") token: String): UserOUT

    @Headers("Content-Type: application/json")
    @GET("users/{id}")
    suspend fun getUserById(@Header("Authorization") token: String, @Path("id") id: Int): UserOUT

    @Headers("Content-Type: application/json")
    @POST("users/")
    suspend fun createUser(@Body user: UserCreationDTO): UserAuthDTO

}