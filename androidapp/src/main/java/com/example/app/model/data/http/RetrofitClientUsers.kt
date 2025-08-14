package com.example.app.model.data.http

import com.example.app.model.handlers.AuthInterceptor
import com.example.app.model.handlers.TokenAuthenticator
import com.example.app.model.services.AuthService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import serialization.AppJson

class RetrofitClientUsers(authService: AuthService) {
    private val BASE_URL = "http://10.0.2.2:9000/http/" // idealmente num .env

    // ⚡ Criar o OkHttpClient com interceptor
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authService))
            .authenticator(TokenAuthenticator(authService))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val usersRetrofitAPI: ApiServiceUsers by lazy {
        retrofit.create(ApiServiceUsers::class.java)
    }
}
