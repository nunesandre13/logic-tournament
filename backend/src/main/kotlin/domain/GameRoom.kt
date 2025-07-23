package domain

import core.Game
import core.Player

data class GameRoom (
    val id : Id,
    val players : List<Player>,
    var game : Game
)