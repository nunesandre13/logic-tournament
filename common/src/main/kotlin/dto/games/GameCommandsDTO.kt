package dto

import dto.interfaces.Command
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.GameType
import model.Id

@Serializable
sealed interface GameCommandsDTO : Command {
    // --- Comandos de Matchmaking ---
    @Serializable
    @SerialName("MatchingCommand")
    sealed interface MatchingCommandDTO : Command {
        val player: PlayerDTO
        val gameType: GameType

        @Serializable
        @SerialName("REQUEST_MATCH")
        data class RequestMatch(
            override val player: PlayerDTO,
            override val gameType: GameType
        ) : MatchingCommandDTO

        @Serializable
        @SerialName("CANCEL_MATCH_SEARCHING")
        data class CancelMatchSearchingDTO(
            override val player: PlayerDTO,
            override val gameType: GameType
        ) : MatchingCommandDTO
    }

    // --- Comandos de Jogo ---
    @Serializable
    @SerialName("PlayCommand")
    sealed interface PlayCommandDTO : Command {
        val player: PlayerDTO
        val gameType: GameType
        val roomId: Id?

        @Serializable
        @SerialName("MAKE_MOVE")
        data class MakeMove(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: Id?,
            val gameMove: MoveDTO
        ) : PlayCommandDTO

        @Serializable
        @SerialName("RESIGN")
        data class Resign(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("PASS")
        data class Pass(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("OFFER_DRAW")
        data class OfferDraw(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("ACCEPT_DRAW")
        data class AcceptDraw(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("GET_GAME_STATUS")
        data class GetGameStatus(
            override val player: PlayerDTO,
            override val roomId: Id?,
            override val gameType: GameType
        ) : PlayCommandDTO
    }
}