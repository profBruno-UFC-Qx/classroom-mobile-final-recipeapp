package com.example.recipeapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp

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
        composable(BottomNavItem.Favorites.route) {
            val authState = authViewModel.state.collectAsState().value
            val user = authState.user

            if(user == null){
                return@composable
            }
            FavoritesScreen(
                uid = user.uid,
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