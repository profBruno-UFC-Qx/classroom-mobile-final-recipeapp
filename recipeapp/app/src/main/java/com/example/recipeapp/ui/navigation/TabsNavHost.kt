package com.example.recipeapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesScreen
import com.example.recipeapp.ui.screens.HomeScreen.HomeScreen
import com.example.recipeapp.ui.screens.MyRecipesScreen.MyRecipesScreen
import com.example.recipeapp.ui.screens.PefilScreen.PerfilScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesViewModel
import com.example.recipeapp.ui.screens.RecipeDetailScreen.RecipeDetailScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TabsNavHost(navController: NavHostController, authViewModel: AuthViewModel) {
    val favoritesViewModel: FavoritesViewModel = viewModel()
    // Auth State
    val authState = authViewModel.state.collectAsState().value
    val user = authState.user

    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            if (user == null){
                return@composable
            }
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                HomeScreen(
                    authViewModel = authViewModel,
                    onLogout = {authViewModel.logout()},
                    uid = user.uid,
                    favoritesViewModel = favoritesViewModel,
                    navController = navController,
                )
            }
        }
        composable(BottomNavItem.MyRecipes.route) {
            if (user == null){
                return@composable
            }
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                MyRecipesScreen(
                    onLeftClick = {
                        navController.popBackStack()
                    },
                    uid = user.uid,
                    favoritesViewModel = favoritesViewModel,
                    navController = navController
                )
            }
        }
        composable(BottomNavItem.Favorites.route) {
            if(user == null){
                return@composable
            }
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                FavoritesScreen(
                    uid = user.uid,
                    onLeftClick = {
                        navController.popBackStack()
                    },
                    viewModel = favoritesViewModel,
                    navController = navController
                )
            }
            }
        composable(BottomNavItem.Perfil.route) {
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                PerfilScreen(
                    onLeftClick = {
                    navController.popBackStack()
                    }
                )
            }
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
    }
}