import dto.*
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
        // Mapeamento para a interface base 'WebSocketResponse'
        polymorphic(WebSocketResponse::class) {
            // Commands
            subclass(Command::class)
            subclass(GameCommandsDTO.MatchingCommandDTO.RequestMatch::class)
            subclass(GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.MakeMove::class)
            subclass(GameCommandsDTO.PlayCommandDTO.Resign::class) // Register specific command implementations
            subclass(GameCommandsDTO.PlayCommandDTO.Pass::class) // Register specific command implementations
            subclass(GameCommandsDTO.PlayCommandDTO.OfferDraw::class) // Register specific command implementations
            subclass(GameCommandsDTO.PlayCommandDTO.AcceptDraw::class) // Register specific command implementations
            subclass(GameCommandsDTO.PlayCommandDTO.GetGameStatus::class) // Register specific command implementations

            // Data
            subclass(Data::class) // Register the Data interface as a subclass of WebSocketResponse


            // Events
            subclass(Event::class) // Register the Event interface as a subclass of WebSocketResponse
            subclass(HeartBeat::class)

        }


        polymorphic(Command::class) {
            subclass(GameCommandsDTO.MatchingCommandDTO::class) // Register the sealed interface
            subclass(GameCommandsDTO.PlayCommandDTO::class) // Register the sealed interface
        }

        polymorphic(GameCommandsDTO.MatchingCommandDTO::class) {
            subclass(GameCommandsDTO.MatchingCommandDTO.RequestMatch::class)
            subclass(GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO::class)
        }

        polymorphic(GameCommandsDTO.PlayCommandDTO::class) {
            subclass(GameCommandsDTO.PlayCommandDTO.MakeMove::class)
            subclass(GameCommandsDTO.PlayCommandDTO.Resign::class)
            subclass(GameCommandsDTO.PlayCommandDTO.Pass::class)
            subclass(GameCommandsDTO.PlayCommandDTO.OfferDraw::class)
            subclass(GameCommandsDTO.PlayCommandDTO.AcceptDraw::class)
            subclass(GameCommandsDTO.PlayCommandDTO.GetGameStatus::class)
        }

        polymorphic(GameResultDTO::class) {
            subclass(GameResultDTO.Ongoing::class)
            subclass(GameResultDTO.Draw::class)
            subclass(GameResultDTO.Win::class)
        }

        polymorphic(MoveDTO::class) {
            subclass(MoveDTO.CheckersMoveDTO::class)
        }
    }
}