package webApi.webSockets

import model.Command
import model.GameType
import model.Player
import games.TicTacToe.TicTacToeMove
import model.Id
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
        val command: Command = when (readCMD[0]) {
            "MAKE_MOVE" -> {
                val position1 = readCMD[1].toInt()
                val position2 = readCMD[2].toInt()
                Command.PlayCommand.MakeMove(player, gameType, Id(roomId!!), TicTacToeMove(position1, position2))
            }

            "RESIGN" -> Command.PlayCommand.Resign(player, gameType, Id(roomId!!))
            "PASS" -> Command.PlayCommand.Pass(player, gameType, Id(roomId!!))
            "OFFER_DRAW" -> Command.PlayCommand.OfferDraw(player, gameType, Id(roomId!!))
            "ACCEPT_DRAW" -> Command.PlayCommand.AcceptDraw(player, gameType, Id(roomId!!))
            "GET_GAME_STATUS" -> Command.PlayCommand.GetGameStatus(player, Id(roomId!!), gameType)
            "REQUEST_MATCH" -> Command.MatchingCommand.RequestMatch(player, gameType)
            "CANCEL_MATCH_SEARCHING" -> Command.MatchingCommand.CancelMatchSearching(player, gameType)
            else -> error("Comando desconhecido: $readCMD")
        }
        // Serializa com o AppJson
        val json = AppJson.encodeToString(Command.serializer(), command)
        println("JSON a enviar: $json")
        ws.send(WsMessage(json))
    }

}