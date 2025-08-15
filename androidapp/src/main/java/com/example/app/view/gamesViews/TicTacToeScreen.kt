package com.example.app.view.gamesViews

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import games.TicTacToe.TicTacToeGame

@Composable
fun TicTacToeScreen(
    game: TicTacToeGame,
    onCellClick: (row: Int, col: Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Jogador atual: ${game.currentPlayer.name}",
            style = MaterialTheme.typography.titleMedium
        )

        // Tabuleiro
        Column(
            modifier = Modifier.weight(1f).padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            for (row in 0..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (col in 0..2) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .border(1.dp, Color.Black)
                                .clickable(enabled = game.board[row][col] == ' ') {
                                    onCellClick(row, col)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = game.board[row][col].toString(),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        }

        // Botão para voltar
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Voltar")
        }
    }
}
