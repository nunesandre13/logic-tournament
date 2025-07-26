package webApi.webSockets.games

import core.Command
import core.Game
import domain.CommandResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.PolymorphicSerializer
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import org.http4k.websocket.WsMessage
import serialization.AppJson
import services.GameService
import java.util.concurrent.CopyOnWriteArrayList
import kotlinx.serialization.encodeToString

class GameWebSocketHandler(val gameServices: GameService) {

    // por agora algo do genero
    private val connections = CopyOnWriteArrayList<Websocket>()

    val gameWebSocketHandler = websockets { webSocket ->

        connections += webSocket

        webSocket.onClose {
            TODO()
        }
        webSocket.onMessage { message ->
            val command = AppJson.decodeFromString<Command>(message.body.toString())
            when (val result = gameServices.receiveCmd(command)) {
                is CommandResult.ActionResult -> TODO()
                is CommandResult.Error -> TODO()
                is CommandResult.MatchError -> TODO()
                is CommandResult.MatchSucceed -> {
                    val gameFlow = gameServices.getStateChange(result.gameRoomId, result.gameType)
                    CoroutineScope(Dispatchers.IO).launch {
                        gameFlow.collect { game ->
                            webSocket.send(WsMessage(AppJson.encodeToString(game)))
                        }
                    }
                }
                is CommandResult.Success -> TODO()
            }
        }

        webSocket.onError {
            TODO()
        }
    }
}