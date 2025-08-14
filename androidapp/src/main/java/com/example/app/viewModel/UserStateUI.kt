package com.example.app.viewModel

import domain.User
import domain.UserAuthResponse

// Example State sealed class for the UI
sealed class UserStateUI {
    object Loading : UserStateUI()
    object LoggedOut : UserStateUI()
    data class LoggedIn(val user: User) : UserStateUI()
    data class Error(val message: String) : UserStateUI()
}