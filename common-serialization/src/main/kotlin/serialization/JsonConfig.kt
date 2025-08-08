package serialization

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
        polymorphic(WebSocketMessage::class) {
            // Commands
            subclass(GameCommandsDTO.MatchingCommandDTO.RequestMatchDTO::class)
            subclass(GameCommandsDTO.MatchingCommandDTO.CancelMatchSearchingDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.MakeMoveDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.ResignDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.PassDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.OfferDrawDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.AcceptDrawDTO::class)
            subclass(GameCommandsDTO.PlayCommandDTO.GetGameStatusDTO::class)

            // Data
            // Register the Data interface as a subclass of WebSocketResponse

            subclass(GameActionResultDTO.SuccessDTO::class)
            subclass(GameActionResultDTO.InvalidMoveDTO::class)
            subclass(GameActionResultDTO.NotYourTurnDTO::class)
            subclass(GameActionResultDTO.GameEndedDTO::class)
            subclass(GameActionResultDTO.InvalidCommandDTO::class)

            // Events
            // Register the Event interface as a subclass of WebSocketResponse
            subclass(HeartBeat::class)
            subclass(MessageEvent::class)
            subclass(GameDTO.TicTacToeGameDTO::class)
            subclass(MatchResultDTO.SuccessDTO::class)
            subclass(MatchResultDTO.FailureDTO::class)
        }
    }
}