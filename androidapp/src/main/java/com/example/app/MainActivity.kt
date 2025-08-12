package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.model.data.http.DataSourceUsers
import com.example.app.model.data.http.RetrofitClient
import com.example.app.model.services.UserServices
import com.example.app.view.userViews.UserScreen
import com.example.app.viewModel.UserViewModel

class MainActivity : ComponentActivity() {

    private val apiUsers by lazy { RetrofitClient.usersRetrofitAPI }
    private val dataSourceUsers by lazy { DataSourceUsers(apiUsers) }
    private val services by lazy { UserServices(dataSourceUsers) }

    private val userViewModel: UserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return UserViewModel(services) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A Surface é o container principal da sua UI
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama o seu ecrã principal com o ViewModel
                    UserScreen(viewModel = userViewModel)
                }
            }
        }
    }
}