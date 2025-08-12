package com.example.app.view.userViews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.app.view.ErrorView
import com.example.app.viewModel.UserStateUI
import com.example.app.viewModel.UserViewModel

@Composable
fun UserScreen(viewModel: UserViewModel) {
    val userState by viewModel.userState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        when (userState) {
            is UserStateUI.LoggedIn -> {
                val user = (userState as UserStateUI.LoggedIn).user
                LoggedInView(
                    user = user,
                    onLogoutClicked = { viewModel.logout() }
                )
            }
            is UserStateUI.LoggedOut -> {

                var clickState by remember { mutableStateOf<ClickStatus>(ClickStatus.NO_CLICKED) }

                when (clickState) {
                    ClickStatus.LOGIN_CLICKED -> LoginView(viewModel, onLoginSuccess = {},
                        onNavigateToCreateUser = {clickState = ClickStatus.CRATED_CLICKED
                            println("Clicked $clickState")
                        })

                    ClickStatus.CRATED_CLICKED -> CreateUserView(viewModel, onUserCreated = {},
                        onNavigateToLogin = { clickState = ClickStatus.LOGIN_CLICKED
                            println("Clicked $clickState")})

                    ClickStatus.NO_CLICKED -> LoggedOutView(
                        onLoginClicked = {
                            clickState = ClickStatus.LOGIN_CLICKED
                            println("Clicked $clickState")
                        },
                        onCreateUserClicked = {
                            clickState = ClickStatus.CRATED_CLICKED
                            println("Clicked $clickState")
                        }
                    )
                }

            }
            is UserStateUI.Error -> {
                val errorMessage = (userState as UserStateUI.Error).message
                ErrorView(errorMessage = errorMessage)
            }
            // Add a Loading state if you want to show a spinner during API calls
            // is UserStateUI.Loading -> {
            //    LoadingView()
            // }
        }
    }
}

enum class ClickStatus {LOGIN_CLICKED,CRATED_CLICKED, NO_CLICKED}