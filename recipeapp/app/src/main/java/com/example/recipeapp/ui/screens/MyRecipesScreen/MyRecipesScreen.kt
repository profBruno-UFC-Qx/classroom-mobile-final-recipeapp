package com.example.recipeapp.ui.screens.MyRecipesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.ui.components.RecipeCard
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesViewModel


@Composable
fun MyRecipesScreen(
    viewModel: MyRecipeViewModel = viewModel(),
    onLeftClick: () -> Unit,
    uid: String,
    favoritesViewModel: FavoritesViewModel,
    navController: NavController
){
    val recipes by viewModel.recipes.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(uid) {
        viewModel.loadRecipes(uid)
    }
    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            HeaderComponent(
                tittle = "Minhas Receitas",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_clipboard,
                onLeftClick = {
                    onLeftClick()
                },
                isLogo = false
            )

            Spacer(modifier = Modifier.height(22.dp))

            when {
                loading ->{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Carregando receitas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                recipes.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Nenhuma receita cadastrada.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(recipes) {recipe ->
                            RecipeCard(
                                recipe = recipe,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
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