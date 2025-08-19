package domain.games

import domain.Player

sealed interface GameCommands {
    val player: Player
    val gameType: GameType
    sealed interface MatchingCommand : GameCommands {

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

        data class QuitGame(
            override val player: Player,
            override val gameType: GameType
        ) : PlayCommand
    }
}
