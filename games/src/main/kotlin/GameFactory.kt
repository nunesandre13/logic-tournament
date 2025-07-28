import model.Game
import model.GameType
import model.Player
import games.TicTacToe.TicTacToeGame
import model.IGameFactory

class GameFactory: IGameFactory {

    override fun createGame(gameType: GameType, players: List<Player>): Game {
        if (players.size != 2) {
            throw IllegalArgumentException("Para ${gameType.name}, são necessários exatamente 2 jogadores.")
        }

        return when (gameType) {
            GameType.TIC_TAC_TOE -> {
                // Instancia o TicTacToeGame.
                // Aqui você pode passar o estado inicial do tabuleiro, jogadores, etc.
                TicTacToeGame(players = players, currentPlayer = players.random())
            }

            else -> throw IllegalArgumentException("GameType '${gameType}' não suportado pelo GameFactory.")
        }
    }
}