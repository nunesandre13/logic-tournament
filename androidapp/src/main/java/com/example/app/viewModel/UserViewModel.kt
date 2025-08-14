package com.example.app.viewModel

// Example UserViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.services.AuthService
import com.example.app.model.services.UserServices
import domain.Email
import domain.Id
import domain.Tokens
import domain.User
import domain.UserAuthResponse
import dto.LogInUserDTO
import dto.UserCreationDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import toDomain

class UserViewModel(
    private val userServices: UserServices,
    private val authService: AuthService
) : ViewModel() {

    private val _userState = MutableStateFlow<UserStateUI>(UserStateUI.Loading)

    val userState: StateFlow<UserStateUI> = _userState

    init {
        viewModelScope.launch {
            try {
                val token = authService.refreshToken()
                    ?: throw Exception("Token is null")
                _userState.value = UserStateUI.LoggedIn(User(Id(1111111), "DUMMY", Email("Dummy e-mail@example.com")))
            }catch (e: Exception) {
                _userState.value = UserStateUI.LoggedOut
            }
        }
    }


    fun login(loginUser: LogInUserDTO) {
        viewModelScope.launch {
            try {

                val userAuth = authService.login(loginUser) ?: throw Exception("Error login")

                _userState.value = UserStateUI.LoggedIn(userAuth.user)
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
                authService.saveTokens(userAuth.tokens)
                _userState.value = UserStateUI.LoggedIn(userAuth.user)
            } catch (e: Exception) {
                // Handle login failure
                _userState.value = UserStateUI.Error(e.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        authService.clearTokens()
        _userState.value = UserStateUI.LoggedOut
    }

}
