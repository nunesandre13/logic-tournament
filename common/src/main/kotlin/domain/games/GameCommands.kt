package domain.games

import domain.Move
import domain.Player

sealed interface GameCommands {

    sealed interface MatchingCommand : GameCommands {
        val player: Player
        val gameType: GameType

        data class RequestMatch(
            override val player: Player,
            override val gameType: GameType
        ) : MatchingCommand

        data class CancelMatchSearching(
            override val player: Player,
            override val gameType: GameType
        ) : MatchingCommand
    }

    sealed interface PlayCommand : GameCommands {
        val player: Player
        val gameType: GameType

        data class MakeMove(
            override val player: Player,
            override val gameType: GameType,
            val gameMove: Move
        ) : PlayCommand

        data class Resign(
            override val player: Player,
            override val gameType: GameType
        ) : PlayCommand


        data class Pass(
            override val player: Player,
            override val gameType: GameType
        ) : PlayCommand

        data class OfferDraw(
            override val player: Player,
            override val gameType: GameType,
        ) : PlayCommand


        data class AcceptDraw(
            override val player: Player,
            override val gameType: GameType
        ) : PlayCommand

        data class GetGameStatus(
            override val player: Player,
            override val gameType: GameType
        ) : PlayCommand
    }
}
