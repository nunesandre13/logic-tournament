package services

import org.example.data.DataMem.PlayerDataMem
import org.example.domain.PlayerDetails

class PlayerServices(private val playerDataMem: PlayerDataMem) {
    fun getPlayer(id: Long): PlayerDetails? = playerDataMem.findById(id)
    fun createPlayer(userName: String, email: String): PlayerDetails = playerDataMem.save(userName, email)
    fun listPlayers(): List<PlayerDetails> = playerDataMem.findAll()
}