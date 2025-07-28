package services.dataStructures

import model.Game
import model.Player
import model.Id
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


// sincronizador de criacao, acessos, e leituras as games rooms

class GameRoomsDataStructure() {

    private val rooms = HashMap<Id, GameRoom>()

    private val mutex = ReentrantLock()

    data class Awaiting(val id: Id, val condition: Condition, var isDone: Boolean = false)

    private val awaitingChanges = HashMap<Id, MutableList<Awaiting>>()

    fun getById(id : Id) : GameRoom {
        mutex.withLock {

            // fast path
            val existing = rooms[id]
            if (existing != null) {
                return existing
            }

            val awaiting = Awaiting(id, mutex.newCondition())

            if (awaitingChanges.containsKey(id)) {
                awaitingChanges[id]?.add(awaiting)
            }else {
                awaitingChanges[id] = mutableListOf(awaiting)
            }

            while (true){
                try {
                    awaiting.condition.await()
                }catch(e : InterruptedException){
                    if (awaiting.isDone){
                        Thread.currentThread().interrupt()
                        return rooms[id] ?: throw IllegalStateException("Not good")
                    }
                    awaitingChanges[id]?.remove(awaiting)
                    throw e
                }
                if (awaiting.isDone) {
                    return rooms[id] ?: throw IllegalStateException("Not good")
                }
            }
        }
    }

    fun destroy(id : Id) {
        mutex.withLock {
            rooms.remove(id)
        }
    }

    fun createGameRoom(game: Game, players : List<Player>, id: Id): GameRoom {
        mutex.withLock {
            val gameRoom = GameRoom(id, players, game)
            rooms[id] = gameRoom
            awaitingChanges[id]?.forEach {
                it.isDone = true
                it.condition.signal()
            }
            awaitingChanges.remove(id)
            return gameRoom
        }
    }
}
