package com.example.recipeapp.ui.screens.MyRecipesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyRecipesScreen(
    onLeftClick: () -> Unit
){
    Scaffold {
        Column() {
            HeaderComponent(
                tittle = "Minhas Receitas",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_clipboard,
                onLeftClick = {
                    onLeftClick()
                },
                isLogo = false
            )
            Text(text = "My Recipes Screen")
        }
    }
}