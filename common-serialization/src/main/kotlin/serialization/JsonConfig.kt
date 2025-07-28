package serialization

import model.Command
import model.Game
import model.Move
import games.TicTacToe.TicTacToeGame
import games.TicTacToe.TicTacToeMove
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


val AppJson = Json {
    prettyPrint = true          // Para JSON formatado e fácil de ler (desativar em produção para otimização)
    ignoreUnknownKeys = true    // Ignora campos no JSON que não existem na sua classe Kotlin
    coerceInputValues = true    // Tenta converter tipos (ex: "1" string para 1 int)

    // *** MÓDULO DE SERIALIZADORES PARA POLIMORFISMO ***
    serializersModule = SerializersModule {
        // Mapeamento para a interface base 'model.Command'
        polymorphic(Command::class) {
            subclass(Command.MatchingCommand.RequestMatch::class, Command.MatchingCommand.RequestMatch.serializer())
            subclass(Command.MatchingCommand.CancelMatchSearching::class, Command.MatchingCommand.CancelMatchSearching.serializer())
            subclass(Command.PlayCommand.MakeMove::class, Command.PlayCommand.MakeMove.serializer())
            subclass(Command.PlayCommand.Resign::class, Command.PlayCommand.Resign.serializer())
            subclass(Command.PlayCommand.Pass::class, Command.PlayCommand.Pass.serializer())
            subclass(Command.PlayCommand.OfferDraw::class, Command.PlayCommand.OfferDraw.serializer())
            subclass(Command.PlayCommand.AcceptDraw::class, Command.PlayCommand.AcceptDraw.serializer())
            subclass(Command.PlayCommand.GetGameStatus::class, Command.PlayCommand.GetGameStatus.serializer())
        }

        // Mapeamento para a interface base 'Move' (que não é sealed)
        polymorphic(Move::class) {
            subclass(TicTacToeMove::class, TicTacToeMove.serializer())
        }

        polymorphic(Game::class) {
            subclass(TicTacToeGame::class, TicTacToeGame.serializer())
        }
    }
}