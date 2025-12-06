package com.example.recipeapp.ui.screens.HomeScreen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.ui.components.RecipeCard
import com.example.recipeapp.R
import com.example.recipeapp.ui.auth.AuthViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
               authViewModel: AuthViewModel,
               onLogout: () -> Unit
) {
    val ui = viewModel.state.collectAsState().value
    Scaffold(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())) { padding ->
        HeaderComponent(tittle = "Home",
            leftIcon = R.drawable.ic_box_arrow_in_left,
            rightIcon = R.drawable.ic_logo_icon,
            onLeftClick = {
                authViewModel.logout()
                onLogout()
            },
            isLogo = true
        )
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                when {
                    ui.loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    ui.error != null -> {
                        Text(
                            text = "Erro: ${ui.error}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(ui.recipes) {recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onClick = {
                                        // Future navigation
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}