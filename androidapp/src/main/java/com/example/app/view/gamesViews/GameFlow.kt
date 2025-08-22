package com.example.app.view.gamesViews


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.app.view.Screens
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.app.model.services.logger
import com.example.app.viewModel.GameStateUI
import com.example.app.viewModel.GameViewModel
import domain.games.GameType
import games.TicTacToe.TicTacToeGame
import games.TicTacToe.TicTacToeMove

fun NavGraphBuilder.GameFlow(
    navController: NavHostController,
    viewModel: GameViewModel,
) {

    navigation(startDestination = Screens.GAMES_LIST.route , route = Screens.GAMES_ROUTES.route) {
        composable(Screens.GAMES_LIST.route) {
            GameSelectionScreen(gamesTypes = GameType.entries) { gameType ->
                viewModel.connectToGame()
                viewModel.requestGame(viewModel.player, gameType)
                navController.navigate(Screens.TIC_TAC_TOE_GAME.route)
            }
        }

        composable(Screens.TIC_TAC_TOE_GAME.route) {
            val gameState by viewModel.gameState.collectAsStateWithLifecycle()
            Log.d(logger,"state changed to $gameState")
            when (val state = gameState) {
                is GameStateUI.Playing -> {
                    Log.d(logger, "GAMING PLAYING")
                    TicTacToeScreen(game = state.game as TicTacToeGame,
                        { s1, s2 ->  viewModel.makeMove(TicTacToeMove(s1, s2), GameType.TIC_TAC_TOE) },
                        {   viewModel.quitGame(GameType.TIC_TAC_TOE)
                            viewModel.cleanStateUi()
                            navController.popBackStack()
                        },{ viewModel.quitGame(GameType.TIC_TAC_TOE) }
                        )
                }
                is GameStateUI.Loading ->{
                    Log.d(logger, "Loading in gameState...")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                GameStateUI.GameOver -> {
                    GameOverScreen {
                        viewModel.closeGame()
                        viewModel.cleanStateUi()
                        navController.popBackStack()
                    }
                }

                GameStateUI.Idle -> {

                }
            }
        }
    }
}