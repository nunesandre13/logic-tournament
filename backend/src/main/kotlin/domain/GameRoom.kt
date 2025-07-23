package domain

import core.Game
import core.Player
import org.example.commonDomain.Id

data class GameRoom (
    val id : Id,
    val players : List<Player>,
    val game : Game
)