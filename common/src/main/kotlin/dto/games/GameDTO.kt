package dto

import domain.games.GameType

data class GameDTO(val gameType: GameType, val players: List<PlayerDTO>, val result : GameResultDTO, val currentPlayer: PlayerDTO)