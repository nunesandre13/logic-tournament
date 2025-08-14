package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.app.viewModel.GameViewModel
import com.example.app.viewModel.UserViewModel
import androidx.compose.runtime.getValue
import com.example.app.view.gamesViews.GameScreen
import com.example.app.view.userViews.AuthScreen
import com.example.app.viewModel.UserStateUI
import dto.LogInUserDTO
import dto.UserCreationDTO

@Composable
fun RootApp(
    userViewModel: UserViewModel,
    gameViewModel: GameViewModel
) {
    val userState by userViewModel.userState.collectAsState()

    MyAppTheme {
        when (val state = userState) {
            is UserStateUI.Loading -> SplashScreen()
            is UserStateUI.LoggedOut-> AuthScreen(
                onLogin = { email, passoWord -> userViewModel.login(LogInUserDTO(email, passoWord)) },
                onCreateAccount = { name, email, passWord -> userViewModel.createUser(UserCreationDTO(name, email,passWord)) },
            )
            is UserStateUI.LoggedIn -> GameScreen(
                onLogout = { userViewModel.logout() }
            )

            is UserStateUI.Error -> ErrorView(state.message)
        }
    }
}
