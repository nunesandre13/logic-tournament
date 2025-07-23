import core.GameType
import core.Id
import core.Move
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
sealed interface Command { // Nota: Se as implementações diretas estiverem em módulos diferentes, esta também não pode ser sealed.
    // Assumindo que todas as *subclasses diretas* de Command estão neste módulo ou no mesmo projecto.

    // --- Comandos de Matchmaking ---
    @kotlinx.serialization.Serializable
    @SerialName("REQUEST_MATCH")
    data class RequestMatch(val playerId: Id, val gameType: GameType) : Command

    @kotlinx.serialization.Serializable
    @SerialName("CANCEL_MATCH_SEARCHING")
    data class CancelMatchSearching(val playerId: Id, val gameType: GameType) : Command

    // --- Comandos de Ação em Jogo (contexto de GameRoom) ---
    @kotlinx.serialization.Serializable
    @SerialName("MAKE_MOVE")
    data class MakeMove(
        val playerId: Id,
        val roomId: Id,
        val gameMove: Move // O segredo: recebe a interface genérica Move
    ) : Command

    // Comandos de ação específicos, agora com contexto
    @Serializable
    @SerialName("RESIGN")
    data class Resign(val playerId: Id, val roomId: Id) : Command

    @kotlinx.serialization.Serializable
    @SerialName("PASS")
    data class Pass(val playerId: Id, val roomId: Id) : Command

    @kotlinx.serialization.Serializable
    @SerialName("OFFER_DRAW")
    data class OfferDraw(val playerId: Id, val roomId: Id) : Command

    @kotlinx.serialization.Serializable
    @SerialName("ACCEPT_DRAW")
    data class AcceptDraw(val playerId: Id, val roomId: Id) : Command

    // --- Comandos de Estado/Informação do Jogo ---
    @kotlinx.serialization.Serializable
    @SerialName("GET_GAME_STATUS")
    data class GetGameStatus(val playerId: Id, val roomId: Id) : Command

    // --- Comandos de Lobby/Convite (Opcional, se o teu jogo tiver) ---
    @kotlinx.serialization.Serializable
    @SerialName("INVITE_PLAYER")
    data class InvitePlayer(val invitingPlayerId: Id, val invitedPlayerId: Id, val gameType: GameType) : Command

    @kotlinx.serialization.Serializable
    @SerialName("ACCEPT_INVITATION")
    data class AcceptInvitation(val invitedPlayerId: Id, val invitationId: Id) : Command

    @kotlinx.serialization.Serializable
    @SerialName("DECLINE_INVITATION")
    data class DeclineInvitation(val invitedPlayerId: Id, val invitationId: Id) : Command
}
