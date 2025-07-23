package core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.commonDomain.Id

@Serializable
@SerialName("Command")
sealed interface Command {

    // --- Comandos de Matchmaking ---
    @Serializable
    @SerialName("MatchingCommand")
    sealed interface MatchingCommand : Command {
        val player: Player
        val gameType: GameType

        @Serializable
        @SerialName("REQUEST_MATCH")
        data class RequestMatch(
            override val player: Player,
            override val gameType: GameType
        ) : MatchingCommand

        @Serializable
        @SerialName("CANCEL_MATCH_SEARCHING")
        data class CancelMatchSearching(
            override val player: Player,
            override val gameType: GameType
        ) : MatchingCommand
    }

    // --- Comandos de Jogo ---
    @Serializable
    @SerialName("PlayCommand")
    sealed interface PlayCommand : Command {
        val player: Player
        val gameType: GameType
        val roomId: Id?

        @Serializable
        @SerialName("MAKE_MOVE")
        data class MakeMove(
            override val player: Player,
            override val gameType: GameType,
            override val roomId: Id?,
            val gameMove: Move
        ) : PlayCommand

        @Serializable
        @SerialName("RESIGN")
        data class Resign(
            override val player: Player,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommand

        @Serializable
        @SerialName("PASS")
        data class Pass(
            override val player: Player,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommand

        @Serializable
        @SerialName("OFFER_DRAW")
        data class OfferDraw(
            override val player: Player,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommand

        @Serializable
        @SerialName("ACCEPT_DRAW")
        data class AcceptDraw(
            override val player: Player,
            override val gameType: GameType,
            override val roomId: Id?
        ) : PlayCommand

        @Serializable
        @SerialName("GET_GAME_STATUS")
        data class GetGameStatus(
            override val player: Player,
            override val roomId: Id?,
            override val gameType: GameType
        ) : PlayCommand
    }
}
