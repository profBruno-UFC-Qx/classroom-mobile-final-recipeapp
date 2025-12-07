package com.example.recipeapp.ui.navigation

import androidx.annotation.DrawableRes
import com.example.recipeapp.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int
) {
    object Home : BottomNavItem("home", "Página Inicial", R.drawable.ic_house, R.drawable.ic_house_fill)
    object MyRecipes : BottomNavItem("my_recipes", "Minhas Receitas", R.drawable.ic_clipboard, R.drawable.ic_clipboard_fill)
    object Favorites : BottomNavItem("favorites", "Favoritos", R.drawable.ic_heart, R.drawable.ic_heart_fill)
    object Perfil : BottomNavItem("perfil", "Perfil", R.drawable.ic_person, R.drawable.ic_person)
}
