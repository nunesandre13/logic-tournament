//package games.TicTacToe
//
//import model.Command
//import model.GameResult
//import model.GameType
//import model.Player
//import model.Id
//import java.util.*
//
//
//fun main() {
//    println("Bem-vindo ao Tic-Tac-Toe Interativo!")
//
//    // Criar os jogadores
//    val player1 = Player(id = Id(1), name = "Jogador X")
//    val player2 = Player(id = Id(2), name = "Jogador O")
//    val players = listOf(player1, player2)
//
//    // Iniciar um novo jogo de TicTacToe
//    var game: TicTacToeGame = TicTacToeGame(
//        players = players,
//        currentPlayer = player1 // Jogador 1 começa
//    )
//
//    val scanner = Scanner(System.`in`)
//
//    while (game.result == GameResult.Ongoing) {
//        println("\n--- Vez de ${game.currentPlayer.name} (${if (game.currentPlayer == player1) 'X' else 'O'}) ---")
//        printBoard(game)
//
//        println("Movimentos disponíveis: ${game.availableMoves().joinToString { "(${it as TicTacToeMove}.row, ${it.col})" }}")
//
//        var moveMade = false
//        while (!moveMade) {
//            print("Insira a linha (0-2) e coluna (0-2) para a sua jogada, separadas por espaço (ex: 0 0): ")
//            val input = scanner.nextLine().trim()
//
//            if (input.equals("resign", ignoreCase = true)) {
//                println("${game.currentPlayer.name} decidiu resignar.")
//                game = game.play(Command.PlayCommand.Resign(game.currentPlayer, GameType.TIC_TAC_TOE,null))
//                moveMade = true
//                break // Sai do loop de jogada e verifica o resultado do jogo
//            }
//
//            if (input.equals("draw", ignoreCase = true)) {
//                println("${game.currentPlayer.name} ofereceu um empate.")
//                // Para uma oferta de empate real, precisaríamos de mais estado no jogo
//                // para rastrear se uma oferta foi feita e se o outro jogador aceitou.
//                // Por simplicidade neste exemplo interativo:
//                println("No modo simples, isto é apenas uma indicação. Apenas um jogador pode aceitar.")
//                print("Aceita o empate? (sim/nao): ")
//                val acceptInput = scanner.nextLine().trim()
//                if (acceptInput.equals("sim", ignoreCase = true)) {
//                    println("Empate aceite!")
//                    game = game.play(Command.PlayCommand.AcceptDraw(game.currentPlayer,GameType.TIC_TAC_TOE,null))
//                    moveMade = true
//                    break // Sai do loop de jogada e verifica o resultado do jogo
//                } else {
//                    println("Empate recusado. O jogo continua.")
//                    // Não alteramos 'game' nem 'moveMade', para que o jogador atual possa fazer outra jogada ou comando.
//                    continue // Volta ao início do loop para pedir uma jogada ou comando novamente
//                }
//            }
//
//
//            val parts = input.split(" ")
//            if (parts.size == 2) {
//                try {
//                    val row = parts[0].toInt()
//                    val col = parts[1].toInt()
//                    val move = TicTacToeMove(row, col)
//
//                    // Verificar se o movimento é válido antes de tentar jogar
//                    if (game.availableMoves().contains(move)) {
//                        game = game.play(Command.PlayCommand.MakeMove(game.currentPlayer, GameType.TIC_TAC_TOE,null, move))
//                        moveMade = true
//                    } else {
//                        println("Jogada inválida! A posição ($row, $col) está ocupada ou fora dos limites.")
//                    }
//                } catch (e: NumberFormatException) {
//                    println("Entrada inválida. Por favor, insira dois números para linha e coluna.")
//                } catch (e: IllegalArgumentException) {
//                    println("Erro na jogada: ${e.message}")
//                } catch (e: IllegalStateException) {
//                    println("Erro no estado do jogo: ${e.message}")
//                    break // Algo sério aconteceu, sai do loop do jogo
//                }
//            } else {
//                println("Entrada inválida. Por favor, insira dois números ou 'resign'/'draw'.")
//            }
//        }
//    }
//
//    println("\n--- Jogo Terminado! ---")
//    printBoard(game)
//
//    when (val result = game.result) {
//        is GameResult.Win -> println("O vencedor é: ${result.winner.name}!")
//        GameResult.Draw -> println("O jogo terminou em Empate!")
//        GameResult.Ongoing -> println("O jogo ainda está a decorrer (estado inesperado no fim).") // Não deveria acontecer
//    }
//
//    scanner.close()
//}
//
//// Função auxiliar para imprimir o tabuleiro
//fun printBoard(game: TicTacToeGame) {
//    // Aceder ao tabuleiro, que infelizmente não é diretamente acessível na interface Game
//    // mas sabemos que TicTacToeGame tem um. Podemos fazer um cast seguro ou mudar a interface Game
//    // para ter um método que retorne o estado visual do tabuleiro se for algo genérico.
//    // Para este teste, assumindo que podemos aceder ao campo 'board' de TicTacToeGame
//    val boardField = TicTacToeGame::class.java.getDeclaredField("board")
//    boardField.isAccessible = true
//    @Suppress("UNCHECKED_CAST")
//    val board = boardField.get(game) as List<List<Char>>
//
//    println("-------------")
//    for (row in board.indices) {
//        print("| ")
//        for (col in board[row].indices) {
//            print("${board[row][col]} | ")
//        }
//        println()
//        println("-------------")
//    }
//}