package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.app.viewModel.GameViewModel
import com.example.app.viewModel.UserStateUI
import com.example.app.viewModel.UserViewModel
import androidx.compose.runtime.getValue
import com.example.app.view.userViews.AuthFlow
import com.example.app.viewModel.ErrorManager

@Composable
fun AppNavigation(
    navController: NavHostController,
    userViewModel: UserViewModel,
    gameViewModel: GameViewModel,
    errorManager: ErrorManager,
) {
    val userState by userViewModel.userState.collectAsState()

    LaunchedEffect(userState) {
        when (userState) {
            is UserStateUI.LoggedIn -> {
                // Efeito secundário: passa o utilizador para a outra ViewModel
                //gameViewModel.setCurrentUser(userState.user)
                // Efeito secundário: navegação para a tela principal
                navController.navigate("games_list")
            }
            is UserStateUI.LoggedOut -> {
                navController.navigate("auth")
            }
            is UserStateUI.Error -> {
                navController.navigate("error")
            }
            is UserStateUI.Loading -> {
                navController.navigate("splash")
            }
        }
    }

    NavHost(navController = navController, startDestination = Screens.SPLASH.route) {
        AuthFlow(navController, userViewModel)
        ErrorFlow(errorManager)
    }
}
