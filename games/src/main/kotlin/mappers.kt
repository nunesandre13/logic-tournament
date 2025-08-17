import domain.games.Move
import domain.games.Game
import domain.games.GameActionResult
import domain.games.GameCommands
import domain.games.GameData
import domain.games.GameEvent
import domain.games.GameResult
import domain.games.MatchResult
import dto.*
import games.TicTacToe.TicTacToeGame
import games.TicTacToe.TicTacToeMove
import mappers.IGameMappers

class GameMappers : IGameMappers {
    override fun toDomain(event: GameRequest): GameEvent {
        return when (event) {
            is GameDTO -> toDomain(event)
            is MatchResultDTO.FailureDTO -> MatchResult.InvalidMatch(event.error)
            is MatchResultDTO.SuccessDTO -> MatchResult.Match(event.roomID.toDomain())
        }
    }

    override fun toDTO(event: GameEvent): GameRequest {
        when (event) {
            is Game -> return toDTO(event)
            is MatchResult.InvalidMatch -> TODO()
            is MatchResult.Match -> TODO()
        }
    }

    override fun toDomain(data: GameResponse): GameData {
        when (data) {
            is GameActionResultDTO -> return toDomain(data)
        }
    }

    override fun toDTO(data: GameData): GameResponse {
        when (data) {
            is GameActionResult -> return toDTO(data)
        }
    }

    override fun toDomain(gameActionResultDTO: GameActionResultDTO): GameActionResult = when (gameActionResultDTO) {
        is GameActionResultDTO.SuccessDTO -> GameActionResult.Success(toDomain(gameActionResultDTO.game))
        is GameActionResultDTO.InvalidMoveDTO -> GameActionResult.InvalidMove(gameActionResultDTO.message)
        is GameActionResultDTO.NotYourTurnDTO -> GameActionResult.NotYourTurn(gameActionResultDTO.message)
        is GameActionResultDTO.GameEndedDTO -> GameActionResult.GameEnded(gameActionResultDTO.message)
        is GameActionResultDTO.InvalidCommandDTO -> GameActionResult.InvalidCommand(gameActionResultDTO.message)
    }

    override fun toDTO(gameActionResult: GameActionResult): GameActionResultDTO = when (gameActionResult) {
        is GameActionResult.Success -> GameActionResultDTO.SuccessDTO(toDTO(gameActionResult.game))
        is GameActionResult.InvalidMove -> GameActionResultDTO.InvalidMoveDTO(gameActionResult.message)
        is GameActionResult.NotYourTurn -> GameActionResultDTO.NotYourTurnDTO(gameActionResult.message)
        is GameActionResult.GameEnded -> GameActionResultDTO.GameEndedDTO(gameActionResult.message)
        is GameActionResult.InvalidCommand -> GameActionResultDTO.InvalidCommandDTO(gameActionResult.message)
    }

    override fun toDomain(gameCommandsDTO: GameCommandsDTO): GameCommands {
        return when (gameCommandsDTO) {
            is GameCommandsDTO.MatchingCommandDTO -> toMatchCommandDomain(gameCommandsDTO)
            is GameCommandsDTO.PlayCommandDTO -> toPlayCommandDomain(gameCommandsDTO)
        }
    }

    override fun toMatchCommandDomain(matchingCommandDTO: GameCommandsDTO.MatchingCommandDTO): GameCommands {
        return when (matchingCommandDTO) {
            is GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO ->
                GameCommands.MatchingCommand.RequestMatch(
                    player = matchingCommandDTO.player.toDomain(),
                    gameType = matchingCommandDTO.gameType
                )

            is GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO ->
                GameCommands.MatchingCommand.CancelMatchSearching(
                    player = matchingCommandDTO.player.toDomain(),
                    gameType = matchingCommandDTO.gameType
                )
        }
    }

    override fun toPlayCommandDomain(playCommandDTO: GameCommandsDTO.PlayCommandDTO): GameCommands {
        return when (playCommandDTO) {
            is GameCommandsDTO.PlayCommandDTO.MakeMoveDTO ->
                GameCommands.PlayCommand.MakeMove(
                    player = playCommandDTO.player.toDomain(),
                    gameType = playCommandDTO.gameType,
                    gameMove = toDomain(playCommandDTO.gameMove)
                )

            is GameCommandsDTO.PlayCommandDTO.ResignDTO ->
                GameCommands.PlayCommand.Resign(
                    player = playCommandDTO.player.toDomain(),
                    gameType = playCommandDTO.gameType
                )

            is GameCommandsDTO.PlayCommandDTO.PassDTO ->
                GameCommands.PlayCommand.Pass(
                    player = playCommandDTO.player.toDomain(),
                    gameType = playCommandDTO.gameType
                )

            is GameCommandsDTO.PlayCommandDTO.OfferDrawDTO ->
                GameCommands.PlayCommand.OfferDraw(
                    player = playCommandDTO.player.toDomain(),
                    gameType = playCommandDTO.gameType
                )

            is GameCommandsDTO.PlayCommandDTO.AcceptDrawDTO ->
                GameCommands.PlayCommand.AcceptDraw(
                    player = playCommandDTO.player.toDomain(),
                    gameType = playCommandDTO.gameType
                )

            is GameCommandsDTO.PlayCommandDTO.GetGameStatusDTO ->
                GameCommands.PlayCommand.GetGameStatus(
                    player = playCommandDTO.player.toDomain(),
                    gameType = playCommandDTO.gameType
                )
        }
    }

    override fun toDomain(gameResultDTO: GameResultDTO): GameResult {
        return when (gameResultDTO) {
            is GameResultDTO.Ongoing -> GameResult.Ongoing
            is GameResultDTO.Draw -> GameResult.Draw
            is GameResultDTO.Win -> GameResult.Win(winner = gameResultDTO.winner.toDomain())
        }
    }

    override fun toDomain(gameDTO: GameDTO): Game {
        return when (gameDTO) {
            is GameDTO.TicTacToeGameDTO -> TicTacToeGame(
                gameDTO.players.map { it.toDomain() },
                gameDTO.board,
                gameDTO.currentPlayer.toDomain(),
                toDomain(gameDTO.result)
            )
        }
    }

    override fun toDTO(gameCommands: GameCommands, roomId: IdDTO?): Command {
        return when (gameCommands) {
            is GameCommands.MatchingCommand.RequestMatch ->
                GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType
                )

            is GameCommands.MatchingCommand.CancelMatchSearching ->
                GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType
                )

            is GameCommands.PlayCommand.MakeMove ->
                GameCommandsDTO.PlayCommandDTO.MakeMoveDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType,
                    roomId = roomId,
                    gameMove = toDTO(gameCommands.gameMove)
                )

            is GameCommands.PlayCommand.Resign ->
                GameCommandsDTO.PlayCommandDTO.ResignDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType,
                    roomId = roomId
                )

            is GameCommands.PlayCommand.Pass ->
                GameCommandsDTO.PlayCommandDTO.PassDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType,
                    roomId = roomId
                )

            is GameCommands.PlayCommand.OfferDraw ->
                GameCommandsDTO.PlayCommandDTO.OfferDrawDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType,
                    roomId = roomId
                )

            is GameCommands.PlayCommand.AcceptDraw ->
                GameCommandsDTO.PlayCommandDTO.AcceptDrawDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType,
                    roomId = roomId
                )

            is GameCommands.PlayCommand.GetGameStatus ->
                GameCommandsDTO.PlayCommandDTO.GetGameStatusDTO(
                    player = gameCommands.player.toDTO(),
                    gameType = gameCommands.gameType,
                    roomId = roomId
                )
        }
    }

    override fun toDTO(move: Move): MoveDTO {
        return when (move) {
            is TicTacToeMove -> MoveDTO.TicTacToeMoveDTO(move.row, move.col)
            else -> TODO()
        }
    }

    override fun toDomain(moveDTO: MoveDTO): Move {
        return when (moveDTO) {
            is MoveDTO.TicTacToeMoveDTO -> TicTacToeMove(moveDTO.row, moveDTO.col)
        }
    }

    override fun toDTO(domain: GameResult): GameResultDTO {
        return when (domain) {
            is GameResult.Ongoing -> GameResultDTO.Ongoing
            is GameResult.Draw -> GameResultDTO.Draw
            is GameResult.Win -> GameResultDTO.Win(winner = domain.winner.toDTO())
        }
    }

    override fun toDTO(game: Game): GameDTO {
        return when (game) {
            is TicTacToeGame -> GameDTO.TicTacToeGameDTO(
                game.players.map { it.toDTO() },
                game.board,
                game.currentPlayer.toDTO(),
                toDTO(game.result)
            )
            else -> TODO()
        }
    }
}