package com.example.app.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.app.viewModel.GameViewModel
import com.example.app.viewModel.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.app.view.gamesViews.GameSelectionScreen
import com.example.app.view.gamesViews.TicTacToeScreen
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
    var currentScreen by remember { mutableStateOf("GameList") }
    var selectedGame by remember { mutableStateOf<String?>(null) }

    MyAppTheme {
        when (val state = userState) {
            is UserStateUI.Loading -> SplashScreen()

            is UserStateUI.LoggedOut -> AuthScreen(
                onLogin = { email, passWord -> userViewModel.login(LogInUserDTO(email, passWord)) },
                onCreateAccount = { name, email, passWord -> userViewModel.createUser(UserCreationDTO(name, email, passWord)) }
            )

            is UserStateUI.LoggedIn -> {
                when (currentScreen) {
                    "GameList" -> GameSelectionScreen(
                        games = listOf("Tic Tac Toe", "Outro Jogo"),
                        onGameSelected = {
                            selectedGame = it
                            currentScreen = "GameScreen"
                        }
                    )
                    "GameScreen" -> {
                        if (selectedGame == "Tic Tac Toe") {
                            TicTacToeScreen(
                                game = gameViewModel.ticTacToeGame,
                                onCellClick = { row, col -> gameViewModel.playTicTacToe(row, col) },
                                onBack = { currentScreen = "GameList" }
                            )
                        }
                    }
                }
            }

            is UserStateUI.Error -> ErrorView(state.message)
        }
    }
}
