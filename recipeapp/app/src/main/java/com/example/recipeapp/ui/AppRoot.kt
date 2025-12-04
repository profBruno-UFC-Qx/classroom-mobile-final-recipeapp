package com.example.recipeapp.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.screens.HomeScreen.HomeScreen
import com.example.recipeapp.ui.screens.LoginScreen.LoginScreen

@Composable
fun AppRoot(
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.state.collectAsState()

    if (authState.user == null) {
        LoginScreen(
            state = authState,
            onLoginEmail = { email, pass, remember ->
                authViewModel.login(email, pass, remember)
            },
            onGoogleLogin = {}
        )
    } else {
        HomeScreen()
    }
}

