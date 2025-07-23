package serialization

import Command
import core.Move
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val AppJson = Json {
    prettyPrint = true          // Para JSON formatado e fácil de ler (desativar em produção para otimização)
    ignoreUnknownKeys = true    // Ignora campos no JSON que não existem na sua classe Kotlin
    coerceInputValues = true    // Tenta converter tipos (ex: "1" string para 1 int)

    // *** MÓDULO DE SERIALIZADORES PARA POLIMORFISMO ***
    serializersModule = SerializersModule {
        // Mapeamento para a interface base 'Command'
        polymorphic(Command::class) {
            // Liste TODAS as implementações diretas da sua sealed interface Command aqui:
            subclass(Command.RequestMatch::class)
            subclass(Command.CancelMatchSearching::class)
            subclass(Command.MakeMove::class)
            subclass(Command.Resign::class)
            subclass(Command.Pass::class)
            subclass(Command.OfferDraw::class)
            subclass(Command.AcceptDraw::class)
            subclass(Command.GetGameStatus::class)
            subclass(Command.InvitePlayer::class)
            subclass(Command.AcceptInvitation::class)
            subclass(Command.DeclineInvitation::class)
            // Certifique-se de que cada Command.XXX data class/object está listada aqui.
        }

        // Mapeamento para a interface base 'Move' (que não é sealed)
        polymorphic(Move::class) {

        }

    }
}