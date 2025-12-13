package com.example.recipeapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ui.auth.AuthViewModel

@Composable
fun TabsScreen(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    TabsNavHost(navController, authViewModel)

}