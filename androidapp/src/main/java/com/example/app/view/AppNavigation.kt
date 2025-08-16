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
import com.example.app.view.gamesViews.GameFlow
import com.example.app.view.userViews.AuthFlow
import com.example.app.viewModel.ErrorManager
import domain.Player

@Composable
fun AppNavigation(
    navController: NavHostController,
    userViewModel: UserViewModel,
    gameViewModel: GameViewModel,
    errorManager: ErrorManager,
) {
    val userState by userViewModel.userState.collectAsState()

    LaunchedEffect(userState) {
        when (val user = userState) {
            is UserStateUI.LoggedIn -> {
                gameViewModel.setCurrentUser(Player(user.user.id,user.user.name))
                navController.navigate(Screens.GAMES_LIST.route)
            }
            is UserStateUI.LoggedOut -> {
                navController.navigate(Screens.AUTH.route)
            }
            is UserStateUI.Error -> {
                navController.navigate(Screens.ERROR.route)
            }
            is UserStateUI.Loading -> {
                navController.navigate(Screens.SPLASH.route)
            }
        }
    }

    NavHost(navController = navController, startDestination = Screens.SPLASH.route) {
        AuthFlow(navController, userViewModel)
        SplashScreen()
        ErrorFlow(errorManager)
        GameFlow(navController, gameViewModel)
    }
}
