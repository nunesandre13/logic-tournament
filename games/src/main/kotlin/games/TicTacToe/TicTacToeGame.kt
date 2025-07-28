package games.TicTacToe

import domain.*
import domain.games.*

class TicTacToeGame(
    override val players: List<Player>,
    val board: List<List<Char>> = List(3) { List(3) { ' ' } }, // ' ' para vazio, 'X' ou 'O'
    override val currentPlayer: Player,
    override val result: GameResult = GameResult.Ongoing
) : Game {
    override val gameType: GameType = GameType.TIC_TAC_TOE

    override fun play(command: GameCommands.PlayCommand): TicTacToeGame {
        return when (command) {
            is GameCommands.PlayCommand.MakeMove -> makeMove(command.gameMove as TicTacToeMove)
            is GameCommands.PlayCommand.Resign -> handleResign()
            is GameCommands.PlayCommand.OfferDraw -> handleOfferDraw()
            is GameCommands.PlayCommand.AcceptDraw -> handleAcceptDraw()
            else -> throw IllegalArgumentException("Comando desconhecido para Tic-Tac-Toe")
        }
    }

    private fun makeMove(move: TicTacToeMove): TicTacToeGame {
        if (result != GameResult.Ongoing) {
            throw IllegalStateException("O jogo já terminou.")
        }
        if (board[move.row][move.col] != ' ') {
            throw IllegalArgumentException("Célula já ocupada.")
        }

        val newBoard = board.toMutableList().map { it.toMutableList() }
        newBoard[move.row][move.col] = if (players.indexOf(currentPlayer) == 0) 'X' else 'O'

        val newResult = checkGameResult(newBoard)

        val nextTurn = players.first { it != currentPlayer }

        return TicTacToeGame(players, newBoard, nextTurn, newResult)
    }

    private fun handleResign(): TicTacToeGame {
        val winner = players.first { it != currentPlayer }
        return TicTacToeGame(players, board, currentPlayer, GameResult.Win(winner))
    }

    private fun handleOfferDraw(): TicTacToeGame {
        // Implementação para oferecer um empate.
        // Pode ser necessário um estado temporário para gerir a oferta.
        // Para simplificação, pode ser assumido que um OfferDraw implica uma espera por AcceptDraw.
        // Por agora, não altera o estado do jogo se não for aceite imediatamente.
        return this
    }

    private fun handleAcceptDraw(): TicTacToeGame {
        // Implementação para aceitar um empate.
        // Isto deve ser chamado após um OfferDraw.
        return TicTacToeGame(players, board, currentPlayer, GameResult.Draw)
    }


    override fun currentState(): GameState {
        return when (result) {
            GameResult.Ongoing -> GameState.RUNNING
            GameResult.Draw, is GameResult.Win -> GameState.FINISHED
        }
    }

    override fun availableMoves(): List<Move> {
        if (result != GameResult.Ongoing) {
            return emptyList()
        }
        val moves = mutableListOf<Move>()
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                if (board[row][col] == ' ') {
                    moves.add(TicTacToeMove(row, col))
                }
            }
        }
        return moves
    }

    // Lógica para verificar o resultado do jogo (vitória, empate)
    private fun checkGameResult(currentBoard: List<List<Char>>): GameResult {
        val symbols = listOf('X', 'O')

        // Verificar linhas e colunas
        for (symbol in symbols) {
            for (i in 0 until 3) {
                // Linhas
                if (currentBoard[i][0] == symbol && currentBoard[i][1] == symbol && currentBoard[i][2] == symbol) {
                    return GameResult.Win(if (symbol == 'X') players[0] else players[1])
                }
                // Colunas
                if (currentBoard[0][i] == symbol && currentBoard[1][i] == symbol && currentBoard[2][i] == symbol) {
                    return GameResult.Win(if (symbol == 'X') players[0] else players[1])
                }
            }

            // Verificar diagonais
            if (currentBoard[0][0] == symbol && currentBoard[1][1] == symbol && currentBoard[2][2] == symbol) {
                return GameResult.Win(if (symbol == 'X') players[0] else players[1])
            }
            if (currentBoard[0][2] == symbol && currentBoard[1][1] == symbol && currentBoard[2][0] == symbol) {
                return GameResult.Win(if (symbol == 'X') players[0] else players[1])
            }
        }

        // Verificar empate (se não há vencedor e o tabuleiro está cheio)
        val isBoardFull = currentBoard.all { row -> row.all { cell -> cell != ' ' } }
        if (isBoardFull) {
            return GameResult.Draw
        }

        return GameResult.Ongoing
    }
}