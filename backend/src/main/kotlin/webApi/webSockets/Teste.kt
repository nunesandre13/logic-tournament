package webApi.webSockets

import Serializers
import domain.games.GameType
import domain.Player
import games.TicTacToe.TicTacToeMove
import domain.Id
import domain.games.GameCommands
import dto.Command
import dto.Event
import dto.GameDTO
import dto.IdDTO
import games.TicTacToe.TicTacToeGame
import org.http4k.client.WebsocketClient
import org.http4k.core.Uri
import org.http4k.websocket.WsMessage
import toDTO
import toDomain

fun main() {
    println("Enter Player Name")
    val playerName = readln()
    println("Enter Player Id")
    val id = readln()
    val player = Player(Id(id.toLong()), playerName)
    val gameType = GameType.TIC_TAC_TOE

    val serializer = Serializers
    // websocket
    val ws = WebsocketClient.nonBlocking(Uri.of("ws://localhost:9000/games"))

    ws.onMessage {
        val result = with(serializer.webSocketResponseSerializer){ it.bodyString().fromJson()}
        when(result){
            is Command -> {}
            is Event -> { handleEvent(result) }
            else -> {}
        }
        println("Resposta do servidor: ${it.bodyString()}")
    }

    while (true) {
        println("Enter command:")
        val readCMD = readln().split(" ")
        val command: GameCommands = when (readCMD[0]) {
            "MAKE_MOVE" -> {
                val position1 = readCMD.getOrNull(1)?.toIntOrNull() ?: error("Missing row")
                val position2 = readCMD.getOrNull(2)?.toIntOrNull() ?: error("Missing column")
                GameCommands.PlayCommand.MakeMove(player, gameType, TicTacToeMove(position1, position2))
            }

            "RESIGN" -> {
                GameCommands.PlayCommand.Resign(player, gameType)
            }

            "PASS" -> {
                GameCommands.PlayCommand.Pass(player, gameType)
            }

            "OFFER_DRAW" -> {
                GameCommands.PlayCommand.OfferDraw(player, gameType)
            }

            "ACCEPT_DRAW" -> {
                GameCommands.PlayCommand.AcceptDraw(player, gameType)
            }

            "GET_GAME_STATUS" -> {
                GameCommands.PlayCommand.GetGameStatus(player, gameType)
            }

            "REQUEST_MATCH" -> GameCommands.MatchingCommand.RequestMatch(player, gameType)
            "CANCEL_MATCH_SEARCHING" -> GameCommands.MatchingCommand.CancelMatchSearching(player, gameType)
            else -> error("Comando desconhecido: $readCMD")
        }

        val roomId = readlnOrNull()?.toIntOrNull()
        val idRoom = if (roomId != null) IdDTO(roomId.toLong()) else null
        val commandDTO = command.toDTO(idRoom)
        val json = with(serializer.webSocketResponseSerializer) {
            commandDTO.toJson()
        }
        println("JSON a enviar: $json")
        ws.send(WsMessage(json))
    }
}

private fun handleEvent(event: Event) {
    when (event) {
        is GameDTO -> {
            val game = event.toDomain()
            if (game is TicTacToeGame) {
                printBoard(game)
            }
        }
        else -> {}
    }
}

private fun printBoard(game: TicTacToeGame) {
    val boardField = TicTacToeGame::class.java.getDeclaredField("board")
    boardField.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    val board = boardField.get(game) as List<List<Char>>

    println("-------------")
    for (row in board.indices) {
        print("| ")
        for (col in board[row].indices) {
            print("${board[row][col]} | ")
        }
        println()
        println("-------------")
    }
}