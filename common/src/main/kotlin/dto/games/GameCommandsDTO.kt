package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import domain.games.GameType

@Serializable
sealed interface GameCommandsDTO : Command {
    // --- Comandos de Matchmaking ---
    @Serializable
    @SerialName("MatchingCommandDTO")
    sealed interface MatchingCommandDTO :GameCommandsDTO {
        val player: PlayerDTO
        val gameType: GameType

        @Serializable
        @SerialName("REQUEST_MATCH_DTO")
        data class RequestMatchDTO(
            override val player: PlayerDTO,
            override val gameType: GameType
        ) : MatchingCommandDTO

        @Serializable
        @SerialName("CANCEL_MATCH_SEARCHING_DTO")
        data class CancelMatchSearchingDTO(
            override val player: PlayerDTO,
            override val gameType: GameType
        ) : MatchingCommandDTO
    }

    // --- Comandos de Jogo ---
    @Serializable
    @SerialName("PlayCommand_DTO")
    sealed interface PlayCommandDTO : GameCommandsDTO    {
        val player: PlayerDTO
        val gameType: GameType
        val roomId: IdDTO?

        @Serializable
        @SerialName("MAKE_MOVE_DTO")
        data class MakeMoveDTO(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: IdDTO?,
            val gameMove: MoveDTO
        ) : PlayCommandDTO

        @Serializable
        @SerialName("RESIGN_DTO")
        data class ResignDTO(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: IdDTO?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("PASS_DTO")
        data class PassDTO(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: IdDTO?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("OFFER_DRAW_DTO")
        data class OfferDrawDTO(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: IdDTO?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("ACCEPT_DRAW_DTO")
        data class AcceptDrawDTO(
            override val player: PlayerDTO,
            override val gameType: GameType,
            override val roomId: IdDTO?
        ) : PlayCommandDTO

        @Serializable
        @SerialName("GET_GAME_STATUS_DTO")
        data class GetGameStatusDTO(
            override val player: PlayerDTO,
            override val roomId: IdDTO?,
            override val gameType: GameType
        ) : PlayCommandDTO
    }
}