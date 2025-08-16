package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.app.viewModel.GameViewModel
import com.example.app.viewModel.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.app.view.gamesViews.GameSelectionScreen
import com.example.app.view.gamesViews.TicTacToeScreen
import com.example.app.viewModel.ErrorManager
import com.example.app.viewModel.UserStateUI
import dto.LogInUserDTO
import dto.UserCreationDTO

@Composable
fun RootApp(
    userViewModel: UserViewModel,
    gameViewModel: GameViewModel,
    errorManager: ErrorManager
) {
    MyAppTheme {
        val navController = rememberNavController()
       AppNavigation(navController,userViewModel,gameViewModel,errorManager)
    }
}
