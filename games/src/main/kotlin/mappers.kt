import domain.Move
import domain.games.Game
import domain.games.GameActionResult
import domain.games.GameCommands
import domain.games.GameResult
import dto.*
import games.TicTacToe.TicTacToeGame
import games.TicTacToe.TicTacToeMove

fun GameActionResultDTO.toDomain(): GameActionResult = when (this) {
    is GameActionResultDTO.SuccessDTO -> GameActionResult.Success(game.toDomain())
    is GameActionResultDTO.InvalidMoveDTO -> GameActionResult.InvalidMove(message)
    is GameActionResultDTO.NotYourTurnDTO -> GameActionResult.NotYourTurn(message)
    is GameActionResultDTO.GameEndedDTO -> GameActionResult.GameEnded(message)
    is GameActionResultDTO.InvalidCommandDTO -> GameActionResult.InvalidCommand(message)
    else -> throw IllegalArgumentException("Unknown action result type: $this")
}

fun GameActionResult.toDTO(): GameActionResultDTO = when (this) {
    is GameActionResult.Success -> GameActionResultDTO.SuccessDTO(game.toDTO())
    is GameActionResult.InvalidMove -> GameActionResultDTO.InvalidMoveDTO(message)
    is GameActionResult.NotYourTurn -> GameActionResultDTO.NotYourTurnDTO(message)
    is GameActionResult.GameEnded -> GameActionResultDTO.GameEndedDTO(message)
    is GameActionResult.InvalidCommand -> GameActionResultDTO.InvalidCommandDTO(message)
}


fun GameCommandsDTO.toDomain(): GameCommands{
    return when(this) {
        is GameCommandsDTO.MatchingCommandDTO -> this.toDomain1()
        is GameCommandsDTO.PlayCommandDTO -> this.toDomain2()
        else -> throw IllegalArgumentException("Unknown command")
    }
}

fun GameCommandsDTO.MatchingCommandDTO.toDomain1(): GameCommands {
    return when (this) {
        is GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO ->
            GameCommands.MatchingCommand.RequestMatch(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO ->
            GameCommands.MatchingCommand.CancelMatchSearching(
                player = this.player.toDomain(),
                gameType = this.gameType
            )

        else -> {
            TODO()
        }
    }
}

fun GameCommandsDTO.PlayCommandDTO.toDomain2(): GameCommands {
    return when (this) {
        is GameCommandsDTO.PlayCommandDTO.MakeMoveDTO ->
            GameCommands.PlayCommand.MakeMove(
                player = this.player.toDomain(),
                gameType = this.gameType,
                gameMove = this.gameMove.toDomain()
            )
        is GameCommandsDTO.PlayCommandDTO.ResignDTO ->
            GameCommands.PlayCommand.Resign(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.PassDTO ->
            GameCommands.PlayCommand.Pass(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.OfferDrawDTO ->
            GameCommands.PlayCommand.OfferDraw(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.AcceptDrawDTO ->
            GameCommands.PlayCommand.AcceptDraw(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.GetGameStatusDTO ->
            GameCommands.PlayCommand.GetGameStatus(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        else -> {
            TODO()
        }
    }
}

fun GameResultDTO.toDomain(): GameResult {
    return when (this) {
        is GameResultDTO.Ongoing -> GameResult.Ongoing
        is GameResultDTO.Draw -> GameResult.Draw
        is GameResultDTO.Win -> GameResult.Win(winner = this.winner.toDomain())
        else -> {
            TODO()
        }
    }
}

fun GameDTO.toDomain(): Game {
    return when(this){
        is GameDTO.TicTacToeGameDTO -> TicTacToeGame(this.players.map { it.toDomain() }, this.board, this.currentPlayer.toDomain(), this.result.toDomain())
        else -> { TODO()}
    }
}


fun GameCommands.toDTO(roomId: IdDTO? = null): Command {
    return when (this) {
        is GameCommands.MatchingCommand.RequestMatch ->
            GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO(
                player = this.player.toDTO(),
                gameType = this.gameType
            )
        is GameCommands.MatchingCommand.CancelMatchSearching ->
            GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO(
                player = this.player.toDTO(),
                gameType = this.gameType
            )
        is GameCommands.PlayCommand.MakeMove ->
            GameCommandsDTO.PlayCommandDTO.MakeMoveDTO(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId,
                gameMove = this.gameMove.toDTO()
            )
        is GameCommands.PlayCommand.Resign ->
            GameCommandsDTO.PlayCommandDTO.ResignDTO(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.Pass ->
            GameCommandsDTO.PlayCommandDTO.PassDTO(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.OfferDraw ->
            GameCommandsDTO.PlayCommandDTO.OfferDrawDTO(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.AcceptDraw ->
            GameCommandsDTO.PlayCommandDTO.AcceptDrawDTO(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.GetGameStatus ->
            GameCommandsDTO.PlayCommandDTO.GetGameStatusDTO(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )

    }
}

fun Move.toDTO(): MoveDTO {
    return when (this) {
        is TicTacToeMove  -> MoveDTO.TicTacToeMoveDTO(this.row, this.col)
        else -> {
            TODO()
        }
    }
}
fun MoveDTO.toDomain(): Move{
    when(this){
        is MoveDTO.TicTacToeMoveDTO -> return TicTacToeMove(this.row, this.col)
        else -> {
            TODO()
        }
    }
}

fun GameResult.toDTO(): GameResultDTO {
    return when (this) {
        is GameResult.Ongoing -> GameResultDTO.Ongoing
        is GameResult.Draw -> GameResultDTO.Draw
        is GameResult.Win -> GameResultDTO.Win(winner = this.winner.toDTO())
    }
}

fun Game.toDTO(): GameDTO {
    return when(this){
        is TicTacToeGame -> GameDTO.TicTacToeGameDTO(this.players.map { it.toDTO() }, this.board, this.currentPlayer.toDTO(), this.result.toDTO())
        else -> { TODO()}
    }
}
