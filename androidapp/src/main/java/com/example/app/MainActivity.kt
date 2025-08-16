package com.example.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.AppConfig.authService
import com.example.app.AppConfig.gameService
import com.example.app.AppConfig.userServices
import com.example.app.model.services.AuthService

import com.example.app.view.RootApp


import com.example.app.viewModel.GameViewModel
import com.example.app.viewModel.UserViewModel

class MainActivity : ComponentActivity() {

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
        AppConfig.setupAuthService(getSharedPreferences("auth_prefs", MODE_PRIVATE))

        AuthService(AppConfig.dataSourceAuth, AppConfig.preferences)
        setContent {
            RootApp(userViewModel, gameViewModel, AppConfig.errorManager)
        }
    }
}