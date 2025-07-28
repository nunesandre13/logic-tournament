package org.example.data.DataMem

import domain.PlayerDetails
import kotlin.random.Random

class PlayerDataMem {
    private val players = mutableMapOf<Long, PlayerDetails>()

    fun findById(id: Long): PlayerDetails? = players[id]

    fun save(userName: String, email: String): PlayerDetails {
        val id = Random.nextLong()
        val playerDetails = PlayerDetails(id, userName, email)
        players[id] = playerDetails
        return playerDetails
    }

    fun findAll(): List<PlayerDetails> = players.values.toList()
}