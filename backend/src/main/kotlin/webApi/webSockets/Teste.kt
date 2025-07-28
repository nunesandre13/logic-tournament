package webApi.webSockets

import domain.games.GameType
import domain.Player
import games.TicTacToe.TicTacToeMove
import domain.Id
import domain.games.GameCommands
import org.http4k.client.WebsocketClient
import org.http4k.core.Uri
import org.http4k.websocket.WsMessage
import serialization.AppJson


fun main() {
    println("Enter Player Name")
    val playerName = readln()
    println("Enter Player Id")
    val id = readln()
    val player = Player(Id(id.toLong()),playerName)
    val gameType = GameType.TIC_TAC_TOE

    // websocket
    val ws = WebsocketClient.nonBlocking(Uri.of("ws://localhost:9000/games"))
    ws.onMessage {
        println("Resposta do servidor: ${it.bodyString()}")
    }

    while (true) {
        val readCMD = readln().split(" ")
        val roomId = readCMD.getOrNull(3)?.toLongOrNull()
        val command: GameCommands= when (readCMD[0]) {
            "MAKE_MOVE" -> {
                val position1 = readCMD[1].toInt()
                val position2 = readCMD[2].toInt()
                GameCommands.PlayCommand.MakeMove(player, gameType, TicTacToeMove(position1, position2))
            }

            "RESIGN" -> GameCommands.PlayCommand.Resign(player, gameType)
            "PASS" -> GameCommands.PlayCommand.Pass(player, gameType)
            "OFFER_DRAW" -> GameCommands.PlayCommand.OfferDraw(player, gameType)
            "ACCEPT_DRAW" -> GameCommands.PlayCommand.AcceptDraw(player, gameType)
            "GET_GAME_STATUS" -> GameCommands.PlayCommand.GetGameStatus(player, gameType)
            "REQUEST_MATCH" -> GameCommands.MatchingCommand.RequestMatch(player, gameType)
            "CANCEL_MATCH_SEARCHING" -> GameCommands.MatchingCommand.CancelMatchSearching(player, gameType)
            else -> error("Comando desconhecido: $readCMD")
        }
        // Serializa com o AppJson
        val json = AppJson.encodeToString(TODO(), command)
        println("JSON a enviar: $json")
        ws.send(WsMessage(json))
    }

}