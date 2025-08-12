package com.example.app.model.handlers

import com.example.app.model.annotations.RequiresAuth
import com.example.app.model.services.AuthService
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor(
    private val authService: AuthService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        val requiresAuth = request.tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(RequiresAuth::class.java) != null

        if (!requiresAuth) {
            return chain.proceed(request)
        }

        val token = authService.getAccessToken()
        if (token.isNullOrBlank()) {
            throw IllegalStateException("Token não encontrado para rota que requer autenticação")
        }

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}
