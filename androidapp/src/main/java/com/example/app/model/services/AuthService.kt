package com.example.app.model.services

import android.content.SharedPreferences
import com.example.app.model.data.http.interfaces.DataUsers
import domain.Tokens
import domain.UserAuthResponse
import dto.LogInUserDTO

class AuthService(
    private val data: DataUsers,
    private val prefs: SharedPreferences
) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    // ---------------------------
    // Armazenamento local
    // ---------------------------
    fun saveTokens(tokens: Tokens) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, tokens.accessToken).apply()
        prefs.edit().putString(KEY_REFRESH_TOKEN, tokens.refreshToken).apply()
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH_TOKEN, null)

    fun clearTokens() {
        prefs.edit()
            .remove(KEY_ACCESS_TOKEN)
            .remove(KEY_REFRESH_TOKEN)
            .apply()
    }

    fun isLoggedIn(): Boolean = getRefreshToken() != null

    // ---------------------------
    // Requests à API
    // ---------------------------
    suspend fun login(logInUserDTO: LogInUserDTO): UserAuthResponse? {
        return try {
            val login = data.login(logInUserDTO)
            saveTokens(login.tokens)
            login
        }catch (e:Exception){
            null
        }
    }

    fun refreshAccessTokenSync(): Tokens? {
        val refreshToken = getRefreshToken() ?: return null
        return try {
            val tokens = data.refreshTokenSync(refreshToken)
            saveTokens(tokens)
            tokens
        } catch (e: Exception) {
            null
        }
    }
}
