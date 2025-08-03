package mappers

import domain.Move
import domain.games.Game
import domain.games.GameActionResult
import domain.games.GameCommands
import domain.games.GameResult
import dto.*

interface IGameMappers {
    fun toDomain(gameActionResultDTO: GameActionResultDTO): GameActionResult
    fun toDTO(gameActionResult: GameActionResult): GameActionResultDTO

    fun toDomain(gameCommandsDTO: GameCommandsDTO): GameCommands
    fun toMatchCommandDomain(matchingCommandDTO: GameCommandsDTO.MatchingCommandDTO): GameCommands
    fun toPlayCommandDomain(playCommandDTO: GameCommandsDTO.PlayCommandDTO): GameCommands

    fun toDomain(gameResultDTO: GameResultDTO): GameResult
    fun toDomain(gameDTO: GameDTO): Game

    fun toDTO(gameCommands: GameCommands, roomId: IdDTO?): Command

    fun toDTO(move: Move): MoveDTO
    fun toDomain(moveDTO: MoveDTO): Move

    fun toDTO(domain: GameResult): GameResultDTO
    fun toDTO(game: Game): GameDTO
}