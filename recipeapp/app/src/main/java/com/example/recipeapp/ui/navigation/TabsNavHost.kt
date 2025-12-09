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
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesViewModel

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
            HomeScreen(
            authViewModel = authViewModel,
            onLogout = {authViewModel.logout()},
                uid = user.uid,
                favoritesViewModel = favoritesViewModel
        ) }
        composable(BottomNavItem.MyRecipes.route) {
            if (user == null){
                return@composable
            }
                MyRecipesScreen(
                onLeftClick = {
                    navController.popBackStack()
                },
                uid = user.uid,
                favoritesViewModel = favoritesViewModel
        ) }
        composable(BottomNavItem.Favorites.route) {
            if(user == null){
                return@composable
            }
            FavoritesScreen(
                uid = user.uid,
                onLeftClick = {
                    navController.popBackStack()
                },
                viewModel = favoritesViewModel
        ) }
        composable(BottomNavItem.Perfil.route) { PerfilScreen(
            onLeftClick = {
                navController.popBackStack()
            }
        ) }
    }
}