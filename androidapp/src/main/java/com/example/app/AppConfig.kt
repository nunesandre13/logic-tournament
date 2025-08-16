package com.example.app

import android.content.SharedPreferences
import com.example.app.model.data.http.DataSourceAuth
import com.example.app.model.data.http.DataSourceUsers
import com.example.app.model.data.http.RetrofitClientAuth
import com.example.app.model.data.http.RetrofitClientUsers
import com.example.app.model.services.AuthService
import com.example.app.model.services.GameService
import com.example.app.model.services.GameServiceConfig
import com.example.app.model.services.UserServices
import com.example.app.viewModel.ErrorManager

object AppConfig {

    lateinit var preferences: SharedPreferences

    private val authRetrofitAPI by lazy { RetrofitClientAuth().authRetrofitAPI }
    val dataSourceAuth by lazy { DataSourceAuth(authRetrofitAPI) }
    val authService: AuthService by lazy { AuthService(dataSourceAuth, preferences) } // Assumindo que prefs é injetado em outro lugar

    private val usersRetrofitAPI by lazy { RetrofitClientUsers(authService).usersRetrofitAPI }
    private val dataSourceUsers by lazy { DataSourceUsers(usersRetrofitAPI) }
    val userServices: UserServices by lazy { UserServices(dataSourceUsers) }

    // Dependências de Jogo
    val gameService: GameService by lazy { GameService(GameServiceConfig.config) }

    // Outras dependências
    val errorManager: ErrorManager by lazy { ErrorManager() }

    fun setupAuthService(prefs: SharedPreferences) {
        if (this::preferences.isInitialized) return
        preferences = prefs
    }
}