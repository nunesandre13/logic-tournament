package com.example.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.model.data.http.DataSourceAuth
import com.example.app.model.data.http.DataSourceUsers
import com.example.app.model.data.http.RetrofitClientAuth
import com.example.app.model.data.http.RetrofitClientUsers
import com.example.app.model.services.AuthService
import com.example.app.model.services.GameService
import com.example.app.model.services.GameServiceConfig
import com.example.app.model.services.UserServices
import com.example.app.view.RootApp

import com.example.app.viewModel.GameViewModel
import com.example.app.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    private val apiAuth by lazy { RetrofitClientAuth().authRetrofitAPI}
    private val dataSourceAuth by lazy { DataSourceAuth(apiAuth) }

    private lateinit var prefs: SharedPreferences
    private lateinit var authService: AuthService

    private val apiUsers by lazy { RetrofitClientUsers(authService).usersRetrofitAPI}
    private val dataSourceUsers by lazy { DataSourceUsers(apiUsers) }
    private val userServices by lazy { UserServices(dataSourceUsers) }

    private val gameService by lazy { GameService(GameServiceConfig.config) }

    private val userViewModel: UserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return UserViewModel(userServices,authService) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val gameViewModel: GameViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return GameViewModel(gameService) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        authService = AuthService(dataSourceAuth, prefs)
        setContent {
            RootApp(userViewModel, gameViewModel)
        }
    }
}