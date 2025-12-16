package com.example.recipeapp.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.navigation.BottomBar
import com.example.recipeapp.ui.navigation.BottomNavItem
import com.example.recipeapp.ui.screens.AddRecipeScreen.AddRecipeScreen
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesScreen
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesViewModel
import com.example.recipeapp.ui.screens.HomeScreen.HomeScreen
import com.example.recipeapp.ui.screens.LoginScreen.LoginScreen
import com.example.recipeapp.ui.screens.MyRecipesScreen.MyRecipesScreen
import com.example.recipeapp.ui.screens.PefilScreen.PerfilScreen
import com.example.recipeapp.ui.screens.RecipeDetailScreen.RecipeDetailScreen
import com.example.recipeapp.ui.screens.RegisterScreen.RegisterScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppRoot(
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.state.collectAsState()
    val navController = rememberNavController()
    val user = authState.user
    val favoritesViewModel: FavoritesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = if (authState.user == null) "login" else "home"
    ) {

        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
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
                }
            )
        }

        composable(BottomNavItem.Home.route) {
            if (user == null){
                return@composable
            }
                HomeScreen(
                    authViewModel = authViewModel,
                    onLogout = {authViewModel.logout()},
                    uid = user.uid,
                    favoritesViewModel = favoritesViewModel,
                    navController = navController,
                )
        }

        composable(BottomNavItem.MyRecipes.route) {
            if (user == null){
                return@composable
            }
                MyRecipesScreen(
                    onLeftClick = {
                        navController.popBackStack()
                    },
                    uid = user.uid,
                    favoritesViewModel = favoritesViewModel,
                    navController = navController
                )
        }

        composable(BottomNavItem.Favorites.route) {
            if(user == null){
                return@composable
            }
                FavoritesScreen(
                    uid = user.uid,
                    onLeftClick = {
                        navController.popBackStack()
                    },
                    viewModel = favoritesViewModel,
                    navController = navController
                )
        }

        composable(BottomNavItem.Perfil.route) {
                PerfilScreen(
                    onLeftClick = {
                        navController.popBackStack()
                    },
                    navController = navController
                )
        }

        composable("details") { backStackEntry ->
            val recipe = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Recipe>("recipe")

            if (recipe != null) {
                RecipeDetailScreen(
                    recipe = recipe,
                    onLeftClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("AddRecipe") { backStackEntry ->
            AddRecipeScreen(
                onLeftClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

