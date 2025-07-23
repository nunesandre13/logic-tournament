package domain

import core.Player


// para pensar melhor ainda por agora nao, mas provavelmente um objeto de dominio
sealed class MatchMakingResult {
    data class Success(val gameRoom: GameRoom) : MatchMakingResult()
    data class Failure(val error: Exception) : MatchMakingResult()
}
