package com.example.app.view.gamesViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverScreen(  onGoBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Game Over!",
            fontSize = 48.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = "Podes tentar de novo!",
            fontSize = 24.sp
        )
        Button(
            onClick = onGoBack,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(text = "Voltar ao Menu")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameOverScreenPreview() {
    GameOverScreen(onGoBack = {})
}