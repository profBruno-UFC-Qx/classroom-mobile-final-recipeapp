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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.ui.components.RecipeCard
import com.example.recipeapp.R
import com.example.recipeapp.data.repository.FavoriteRepository
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.navigation.BottomBar
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
               authViewModel: AuthViewModel,
               onLogout: () -> Unit,
               uid: String,
               favoritesViewModel: FavoritesViewModel,
               navController: NavController
) {
    val loading = viewModel.state.collectAsState().value.loading
    val error = viewModel.state.collectAsState().value.error
    val recipes = viewModel.state.collectAsState().value.recipes

    LaunchedEffect(uid) {
        favoritesViewModel.loadFavorites(uid)
    }

    Scaffold(
        bottomBar = { BottomBar(navController as NavHostController) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            HeaderComponent(
                tittle = "Tela Inicial",
                leftIcon = R.drawable.ic_box_arrow_in_left,
                rightIcon = R.drawable.ic_logo_icon,
                onLeftClick = {
                    authViewModel.logout()
                    onLogout()
                },
                isLogo = true
            )

            Spacer(modifier = Modifier.height(22.dp))

            when {
                loading -> {
                    Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                error != null -> {
                    Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                        Text(
                            text = "Erro: ${error}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                else -> {

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(recipes) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "recipe",
                                        recipe
                                    )
                                    navController.navigate("details")
                                },
                                uid = uid,
                                viewModel = favoritesViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}