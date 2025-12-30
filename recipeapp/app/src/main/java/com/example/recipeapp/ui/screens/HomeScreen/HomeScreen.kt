package com.example.recipeapp.ui.screens.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

            var search by remember { mutableStateOf("") }
            HeaderComponent(
                tittle = "Tela Inicial",
                leftIcon = R.drawable.ic_box_arrow_in_left,
                rightIcon = R.drawable.ic_logo_icon,
                onLeftClick = {
                    onLogout()
                },
                isLogo = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                    viewModel.searchRecipe(search) },
                label = { Text("Pesquisar") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold),
                leadingIcon = {
                    Image(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Lupa",
                        modifier = Modifier.graphicsLayer(
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary,
                                BlendMode.SrcIn)
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
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

                recipes.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Nenhuma receita encontrada.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
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