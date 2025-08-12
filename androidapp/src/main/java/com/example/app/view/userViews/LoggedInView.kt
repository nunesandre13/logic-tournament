package com.example.app.view.userViews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.Tokens

@Composable
fun LoggedInView(user: Tokens, onLogoutClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome, ${user}!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogoutClicked) {
            Text("Logout")
        }
    }
}