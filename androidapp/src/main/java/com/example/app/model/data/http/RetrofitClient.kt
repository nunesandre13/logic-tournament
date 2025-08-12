package com.example.app.model.data.http

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import serialization.AppJson

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:9000/http/" // should be on a .env

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val usersRetrofitAPI: ApiServiceUsers by lazy {
        retrofit.create(ApiServiceUsers::class.java)
    }
}