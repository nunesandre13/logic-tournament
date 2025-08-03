import domain.games.GameType
import domain.Player
import games.TicTacToe.TicTacToeMove
import domain.Id
import domain.games.GameCommands
import dto.*
import games.TicTacToe.TicTacToeGame
import org.http4k.client.JavaHttpClient
import org.http4k.client.WebsocketClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.websocket.WsMessage
import serializers.UserSerializers.toJson
import toDTO

private val gameMappers = GameMappers()
private val serializer = Serializers
private const val httpURL = "http://localhost:9000/http"
private val client = JavaHttpClient()

fun main() {
    var user : UserOUT? = null
    while (user == null) {
        try {
            user = init()
            println(user.toJson())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    val player = Player(user.id.toDomain(), user.name)
    gameHandler(player)
}

private fun init(): UserOUT{
    println("1 -> logIn")
    println("2 -> create")
    val chose = readln().toInt()
    return if (chose == 1) logIn()
    else createUser()
}
private fun logIn(): UserOUT{
    println("insert you user Id")
    val userID = readln().toInt()
    val request = Request(Method.GET, Uri.of("$httpURL/users/$userID"))
        .header("Content-Type", "application/json")
    val response = client(request)
    return with(serializer.userSerializer){ response.bodyString().toUserOut()}
}

private fun createUser(): UserOUT {
    println("insert you user Name")
    val userName = readln()
    println("Insert your Email")
    val email = readln()
    val request = Request(Method.POST, Uri.of("$httpURL/users"))
        .header("Content-Type", "application/json")
    val userIn = UserIN(userName, email).toJson()
    println(userIn)
    request.body(userIn)
    val response = client(request)
    println(response.status)
    val body = response.bodyString()
    println(body)
    return with(serializer.userSerializer){ body.toUserOut()}
}

private fun gameHandler(player: Player){
    val gameType = GameType.TIC_TAC_TOE
    val ws = WebsocketClient.nonBlocking(Uri.of("ws://localhost:9000/ws/games"))
    ws.onMessage {
        val result = with(serializer.webSocketResponseSerializer){ it.bodyString().fromJson()}
        when(result){
            is Command -> {}
            is Event -> {
                handleEvent(result)
            }
            else -> {}
        }
    }

    val commands = listOf("REQUEST_MATCH","CANCEL_MATCH_SEARCHING","MAKE_MOVE","RESIGN","PASS","OFFER_DRAW","ACCEPT_DRAW","GET_GAME_STATUS" )
    while (true) {
        try {
            printLnCMD(commands)
            println("Enter command:")
            val readCMD = readln().split("/")
            val command: GameCommands = when (commands[readCMD[0].toInt()]) {
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

            val roomId = readCMD.getOrNull(3)?.toIntOrNull()
            val idRoom = if (roomId != null) IdDTO(roomId.toLong()) else null
            val commandDTO = gameMappers.toDTO(command, idRoom)
            val json = with(serializer.webSocketResponseSerializer) {
                commandDTO.toJson()
            }
            println("JSON a enviar: $json")
            ws.send(WsMessage(json))
        }catch (e: Exception){
            println(e.message)
        }
    }
}
private fun printLnCMD(list: List<String>) {
    list.forEachIndexed { i, s ->
        println("($i)$s")
    }
}

private fun handleEvent(event: Event) {
    when (event) {
        is GameDTO -> {
            val game = gameMappers.toDomain(event)
            if (game is TicTacToeGame) {
                printBoard(game)
            }
        }
        is MatchResultDTO.SuccessDTO -> println("your gameRoomId is" + event.roomID)
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
    println("current player: " + game.currentPlayer.name)
}