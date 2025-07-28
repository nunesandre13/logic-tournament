package services.dataStructures

import domain.games.Game
import domain.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import domain.Id
import domain.games.GameActionResult
import domain.games.GameCommands

class GameRoom(val id : Id, val players : List<Player>, game : Game){

    private val _stateFlow = MutableStateFlow(game)

    private val mutex = Mutex()

    val stateFlow: StateFlow<Game> get() = _stateFlow

    suspend fun play(command: GameCommands.PlayCommand):GameActionResult {
        mutex.withLock {
            when(val play = _stateFlow.value.play(command)) {
                is GameActionResult.Success -> {
                    _stateFlow.value = play.game
                    return play
                }
                is GameActionResult.GameEnded -> return play
                is GameActionResult.InvalidCommand -> return play
                is GameActionResult.InvalidMove -> return play
                is GameActionResult.NotYourTurn -> return play
            }
        }
    }

}