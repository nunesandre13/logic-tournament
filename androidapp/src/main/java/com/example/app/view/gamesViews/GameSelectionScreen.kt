package com.example.app.view.gamesViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.games.GameType

@Composable
fun GameSelectionScreen(
    gamesTypes: List<GameType>,
    onGameSelected: (GameType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Escolhe um jogo:", style = MaterialTheme.typography.headlineSmall)
        gamesTypes.forEach { game ->
            Button(
                onClick = { onGameSelected(game) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(game.name)
            }
        }
    }
}
