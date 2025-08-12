package com.example.app.viewModel

// Example UserViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.services.UserServices
import domain.Tokens
import domain.UserAuthResponse
import dto.LogInUserDTO
import dto.UserCreationDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import toDomain

class UserViewModel(
    private val userServices: UserServices
) : ViewModel() {

    private val _userState = MutableStateFlow<UserStateUI>(UserStateUI.LoggedOut)
    val userState: StateFlow<UserStateUI> = _userState

    fun login(loginUser: LogInUserDTO) {
        viewModelScope.launch {
            try {
                // The UserServices returns Tokens, you would likely then use
                // those tokens to fetch user data or store them securely.
                val userAuth = userServices.login(loginUser)

                _userState.value = UserStateUI.LoggedIn(userAuth)
            } catch (e: Exception) {
                // Handle login failure
                _userState.value = UserStateUI.Error(e.message ?: "Login failed")
            }
        }
    }

    fun createUser(userCreationDTO: UserCreationDTO) {
        viewModelScope.launch {
            try {
                // The UserServices returns Tokens, you would likely then use
                // those tokens to fetch user data or store them securely.
                val userAuth  = userServices.createUser(userCreationDTO)

                _userState.value = UserStateUI.LoggedIn(userAuth)
            } catch (e: Exception) {
                // Handle login failure
                _userState.value = UserStateUI.Error(e.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        _userState.value = UserStateUI.LoggedOut
    }

}

// Example State sealed class for the UI
sealed class UserStateUI {
    object LoggedOut : UserStateUI()
    data class LoggedIn(val user: UserAuthResponse) : UserStateUI()
    data class Error(val message: String) : UserStateUI()
    // ... other states like Loading
}