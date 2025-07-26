package services.dataStructures

import core.Command
import core.Game
import core.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.example.commonDomain.Id

class GameRoom(val id : Id, val players : List<Player>, game : Game){

    private val _stateFlow = MutableStateFlow(game)

    private val mutex = Mutex()

    val stateFlow: StateFlow<Game> get() = _stateFlow

    suspend fun play(command: Command.PlayCommand) {
        mutex.withLock {
            _stateFlow.value =  _stateFlow.value.play(command)
        }
    }

}