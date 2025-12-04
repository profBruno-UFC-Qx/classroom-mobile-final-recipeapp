package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.ui.AppRoot
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.auth.AuthViewModelFactory
import com.example.recipeapp.ui.theme.RecipeappTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val factory = AuthViewModelFactory(application)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            RecipeappTheme {
                AppRoot(
                    authViewModel = authViewModel,
                )
            }
        }
    }
}
