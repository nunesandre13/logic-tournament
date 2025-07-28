package services.dataStructures

import domain.Player
import domain.Id
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.random.Random
import kotlin.time.Duration

// sincronizador para players a espera de um match
class AwaitingPlayersSinc (private val timeToLeave : Duration) {
    private data class AwaitingPlayer(
        val condition: Condition,
        val player : Player,
        var matchedPlayer : Player? = null,
        var id : Id? = null)
    private val players = mutableListOf<AwaitingPlayer>()
    private val mutex = ReentrantLock()

    fun getPlayer(player : Player) : MatchingResult{
        mutex.withLock {
            // fast path
            if (players.isNotEmpty()) {
                val myMatchedPlayer = players.removeFirst()
                myMatchedPlayer.matchedPlayer = player
                myMatchedPlayer.id = Id(Random.nextLong())
                myMatchedPlayer.condition.signal()
                return MatchingResult.CreatorSuccess(myMatchedPlayer.player,
                    myMatchedPlayer.id ?: throw IllegalStateException("Player ${myMatchedPlayer.id} not found"))
            }
            var time = timeToLeave.inWholeNanoseconds
            val request = AwaitingPlayer(mutex.newCondition(), player)
            players.add(request)
            while (true){

                try {

                    time = request.condition.awaitNanos(time)

                }catch (e : InterruptedException){

                    if (request.matchedPlayer != null && request.id != null) {
                        Thread.currentThread().interrupt()
                        return MatchingResult.AwaitedSuccess(request.matchedPlayer!!, request.id!!)
                    }
                    players.remove(request)
                    throw e
                }
                if (request.matchedPlayer != null && request.id != null) {
                    return MatchingResult.AwaitedSuccess(request.matchedPlayer!!, request.id!!)
                }
                if (time <= 0){
                    players.remove(request)
                    return MatchingResult.Failure
                }
                if (request !in players){
                    return MatchingResult.Failure
                }
            }
        }
    }

    fun cancelMatchingPlayer(player : Player): Boolean {
        mutex.withLock {
            val playerToRemove = players.find { it.player == player }
            if (playerToRemove != null){
                players.remove(playerToRemove)
                playerToRemove.condition.signal()
                return true
            }
            return false
        }
    }

    sealed class MatchingResult {
        // creator cria a room id e futuramente a room
        data class CreatorSuccess(val matchedPlayer: Player, val roomId: Id) : MatchingResult()
        // Sucesso: a thread atual foi encontrada na fila e o outro jogador já concretizou o match
        data class AwaitedSuccess(val matchedPlayer: Player, val roomId: Id) : MatchingResult()
        data object Failure : MatchingResult()
    }
}