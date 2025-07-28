import domain.Move
import domain.games.Game
import domain.games.GameCommands
import domain.games.GameResult
import dto.*
import games.TicTacToe.TicTacToeMove


fun GameCommandsDTO.MatchingCommandDTO.toDomain(): GameCommands {
    return when (this) {
        is GameCommandsDTO.MatchingCommandDTO.RequestMatch ->
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



fun GameCommandsDTO.PlayCommandDTO.toDomain(): GameCommands {
    return when (this) {
        is GameCommandsDTO.PlayCommandDTO.MakeMove ->
            GameCommands.PlayCommand.MakeMove(
                player = this.player.toDomain(),
                gameType = this.gameType,
                gameMove = this.gameMove.toDomain()
            )
        is GameCommandsDTO.PlayCommandDTO.Resign ->
            GameCommands.PlayCommand.Resign(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.Pass ->
            GameCommands.PlayCommand.Pass(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.OfferDraw ->
            GameCommands.PlayCommand.OfferDraw(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.AcceptDraw ->
            GameCommands.PlayCommand.AcceptDraw(
                player = this.player.toDomain(),
                gameType = this.gameType
            )
        is GameCommandsDTO.PlayCommandDTO.GetGameStatus ->
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
    when (this) {
        return TODO() -> TODO()
    }
}


fun GameCommands.toDTO(roomId: IdDTO): Command {
    return when (this) {
        is GameCommands.MatchingCommand.RequestMatch ->
            GameCommandsDTO.MatchingCommandDTO.RequestMatch(
                player = this.player.toDTO(),
                gameType = this.gameType
            )
        is GameCommands.MatchingCommand.CancelMatchSearching ->
            GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO(
                player = this.player.toDTO(),
                gameType = this.gameType
            )
        is GameCommands.PlayCommand.MakeMove ->
            GameCommandsDTO.PlayCommandDTO.MakeMove(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId,
                gameMove = this.gameMove.toDTO()
            )
        is GameCommands.PlayCommand.Resign ->
            GameCommandsDTO.PlayCommandDTO.Resign(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.Pass ->
            GameCommandsDTO.PlayCommandDTO.Pass(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.OfferDraw ->
            GameCommandsDTO.PlayCommandDTO.OfferDraw(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.AcceptDraw ->
            GameCommandsDTO.PlayCommandDTO.AcceptDraw(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )
        is GameCommands.PlayCommand.GetGameStatus ->
            GameCommandsDTO.PlayCommandDTO.GetGameStatus(
                player = this.player.toDTO(),
                gameType = this.gameType,
                roomId = roomId
            )

    }
}

fun Move.toDTO(): MoveDTO {
    return when (this) {
        is TicTacToeMove  -> MoveDTO.CheckersMoveDTO(from = this.col, to = this.row)
        else -> {
            TODO()
        }
    }
}
fun MoveDTO.toDomain(): Move{
    TODO()
}

fun GameResult.toDTO(): GameResultDTO {
    return when (this) {
        is GameResult.Ongoing -> GameResultDTO.Ongoing
        is GameResult.Draw -> GameResultDTO.Draw
        is GameResult.Win -> GameResultDTO.Win(winner = this.winner.toDTO())
    }
}

fun Game.toDTO(): GameDTO {
    return GameDTO(
        gameType = this.gameType,
        players = this.players.map { it.toDTO() },
        result = this.result.toDTO(),
        currentPlayer = this.currentPlayer.toDTO()
    )
}
