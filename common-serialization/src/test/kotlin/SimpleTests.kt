import model.Game
import model.Player
import games.TicTacToe.TicTacToeGame
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.Json
import model.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import serialization.AppJson

class SimpleTests {

    @Test
    fun testSerialization() {
        val player1 = Player(id = Id(1), name = "Jogador X")
        val player2 = Player(id = Id(2), name = "Jogador O")
        val players = listOf(player1, player2)

        // Iniciar um novo jogo de TicTacToe
        val game: TicTacToeGame = TicTacToeGame(
            players = players,
            currentPlayer = player1 // Jogador 1 começa
        )
        val serialized = AppJson.encodeToString(PolymorphicSerializer(Game::class), game)
        val deserialized = Json.decodeFromString(PolymorphicSerializer(Game::class), serialized)
        assertEquals(game, deserialized)
    }
}