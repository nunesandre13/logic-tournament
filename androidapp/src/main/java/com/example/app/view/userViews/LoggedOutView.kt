package com.example.app.view.userViews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoggedOutView(onLoginClicked: () -> Unit, onCreateUserClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("You are not logged in.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLoginClicked) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onCreateUserClicked) {
            Text("Create Account")
        }
    }
}