package com.example.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.services.GameService
import domain.Id
import domain.Player
import domain.User
import domain.games.Game
import domain.games.GameCommands
import domain.games.GameData
import domain.games.GameEvent
import domain.games.GameType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class GameViewModel(
    private val gameService: GameService
) : ViewModel() {

    private val _gameState = MutableStateFlow<GameStateUI>(GameStateUI.Loading)

    val gameState: StateFlow<GameStateUI> = _gameState


    private val _currentUser = MutableStateFlow<User?>(null)


    val currentUser: StateFlow<User?> = _currentUser


    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }


    init {
        // Collect all flows from GameService and combine them into a single state
        viewModelScope.launch {
            combine(
                gameService.commands,
                gameService.data,
                gameService.events
            ) { commands, data, events ->
                // Here you would process the latest command, data, and event
                // to update the game state.
                // This is a simplified example.
                GameStateUI.Loaded(
                    lastCommand = commands,
                    lastData = data,
                    lastEvent = events
                )
            }.collect { combinedState ->
                _gameState.value = combinedState
            }
        }
    }

    fun connectToGame() {
        gameService.connect()
    }

    fun sendCommand(command: GameCommands, roomId: Id) {
        viewModelScope.launch {
            gameService.sendCommand(command, roomId)
        }
    }

    fun requestGame(player: Player, gameType: GameType) {
        viewModelScope.launch {
            gameService.requestGame(player, gameType)
        }
    }
}

sealed class GameStateUI {
    object Loading : GameStateUI()
    data class Loaded(
        val lastCommand: GameCommands,
        val lastData: GameData,
        val lastEvent: GameEvent
    ) : GameStateUI()
}
