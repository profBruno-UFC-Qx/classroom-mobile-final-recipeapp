package com.example.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesScreen
import com.example.recipeapp.ui.screens.HomeScreen.HomeScreen
import com.example.recipeapp.ui.screens.MyRecipesScreen.MyRecipesScreen
import com.example.recipeapp.ui.screens.PefilScreen.PerfilScreen

@Composable
fun TabsNavHost(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {

        composable(BottomNavItem.Home.route) { HomeScreen(
            authViewModel = authViewModel,
            onLogout = {authViewModel.logout()}
        ) }
        composable(BottomNavItem.MyRecipes.route) { MyRecipesScreen(
            onLeftClick = {
                navController.popBackStack()
            }
        ) }
        composable(BottomNavItem.Favorites.route) { FavoritesScreen(
            onLeftClick = {
                navController.popBackStack()
            }
        ) }
        composable(BottomNavItem.Perfil.route) { PerfilScreen(
            onLeftClick = {
                navController.popBackStack()
            }
        ) }
    }
}