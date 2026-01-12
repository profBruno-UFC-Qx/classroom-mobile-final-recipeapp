package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.dataStore.ThemeManager
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

        val themeManager = ThemeManager(applicationContext)

        val factory = AuthViewModelFactory(application)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        enableEdgeToEdge()

        setContent {
            val isDarkTheme by themeManager.isDarkMode.collectAsState(initial = false)

            RecipeappTheme(darkTheme = isDarkTheme){
                Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                    AppRoot(
                        authViewModel = authViewModel,
                    )
                }
            }
        }
    }
}
