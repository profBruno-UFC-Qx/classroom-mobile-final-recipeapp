package com.example.recipeapp.ui

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.screens.HomeScreen.HomeScreen
import com.example.recipeapp.ui.screens.LoginScreen.LoginScreen
import com.example.recipeapp.ui.screens.RegisterScreen.RegisterScreen

@Composable
fun AppRoot(
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.state.collectAsState()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (authState.user == null) "login" else "home"
    ){
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSucess = {
                    navController.navigate("home"){
                        popUpTo("login") {inclusive = true}
                    }
                },
                navigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                navigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSucess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                authViewModel = authViewModel,
                onLogout = {
                    navController.navigate("login"){
                        popUpTo("home") {inclusive = true}
                    }
                }
            )
        }
    }

}

