package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.app.viewModel.ErrorManager
import com.example.app.viewModel.UserViewModel

@Composable
fun ErrorScreen(vm: ErrorManager) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error: ${vm.errorMessage}",
            color = Color.Red
        )
    }
    vm.clearError()
}

fun NavGraphBuilder.ErrorFlow(vm: ErrorManager) {
    composable(Screens.ERROR.route) {
        ErrorScreen(vm = vm)
    }
}