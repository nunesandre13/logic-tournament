package com.example.app.view.userViews


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app.view.Screens
import com.example.app.viewModel.UserViewModel
import dto.LogInUserDTO
import dto.UserCreationDTO

fun NavGraphBuilder.AuthFlow(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    navigation(startDestination = AuthScreenState.Login().nav.route , route = Screens.AUTH.route) {
        composable(AuthScreenState.Login().nav.route) {
            LoginScreen(
                onLogin = {s1, s2 -> viewModel.login(LogInUserDTO(s1,s2))},
                onNavigateToCreateAccount = {
                    navController.navigate(AuthScreenState.CreateAccount().nav.route)
                }
            )
        }
        composable(AuthScreenState.CreateAccount().nav.route) {
            CreateAccountScreen(
                onCreateAccount = {s1, s2, s3 -> viewModel.createUser(UserCreationDTO(s1,s2,s3))},
                onNavigateToLogin = {
                    navController.navigate(AuthScreenState.Login().nav.route)
                }
            )
        }
    }
}