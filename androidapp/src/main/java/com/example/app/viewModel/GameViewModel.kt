package com.example.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.services.GameService
import com.example.app.model.services.logger
import domain.Id
import domain.Player
import domain.games.Game
import domain.games.GameActionResult
import domain.games.GameCommands
import domain.games.GameType
import domain.games.MatchResult

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GameViewModel(
    private val gameService: GameService
) : ViewModel() {

    private val _gameState = MutableStateFlow<GameStateUI>(GameStateUI.Loading)
    val gameState: StateFlow<GameStateUI> = _gameState


    private val _events = MutableSharedFlow<UiEvent>()
    val events: SharedFlow<UiEvent> = _events

    lateinit var player : Player
        private set

    fun setCurrentUser(player : Player) {
        this.player = player
    }

    suspend fun cleanStateUi(){
      _gameState.emit(GameStateUI.Loading)
    }

    // talvez um flow ou algo do genero
    lateinit var roomId: Id
        private set


    init {
        viewModelScope.launch {
            launch {
                gameService.gameData.collect { data ->
                    when (data) {
                        is GameActionResult.GameEnded -> _gameState.emit(GameStateUI.GameOver)
                        is GameActionResult.InvalidCommand -> _events.emit(UiEvent.ShowMessage(data.message))
                        is GameActionResult.InvalidMove -> _events.emit(UiEvent.ShowMessage(data.message))
                        is GameActionResult.NotYourTurn -> _events.emit(UiEvent.ShowMessage(data.message))
                        is GameActionResult.Success -> _events.emit(UiEvent.ShowMessage("Success!"))
                    }
                }
            }
            launch {
                gameService.gameEvents.collect { event ->
                    when (event) {
                        is Game -> {
                            Log.d(logger, "Game Event: $event, now updating ui")
                            _gameState.emit(GameStateUI.Playing(event))}
                        is MatchResult.InvalidMatch -> _events.emit(UiEvent.ShowAlert("Invalid match!"))
                        is MatchResult.Match ->{
                            Log.d(logger, "Match Event: $event")
                            roomId = event.roomId
                            _events.emit(UiEvent.ShowAlert("Matched!"))}
                    }
                }
            }
        }
    }

    fun connectToGame() {
        gameService.connect()
        viewModelScope.launch {
            _events.emit(UiEvent.ShowAlert("Connected!"))
        }
    }

    fun sendCommand(command: GameCommands, roomId: Id) {
        viewModelScope.launch {
            gameService.sendCommand(command, roomId)
        }
    }

    fun requestGame(player: Player, gameType: GameType) {
        viewModelScope.launch {
            Log.d(logger, "requesting game")
            gameService.requestGame(player, gameType)
        }
    }
}

sealed class GameStateUI {
    object Loading : GameStateUI()
    data class Playing(val game: Game) : GameStateUI()
    object GameOver : GameStateUI()
}

sealed class UiEvent {
    data class ShowMessage(val text: String) : UiEvent()
    data class ShowAlert(val text: String) : UiEvent()
}
