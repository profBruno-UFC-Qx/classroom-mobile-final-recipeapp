package com.example.recipeapp.ui.screens.RecipeDetailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.R
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onLeftClick: () -> Unit
) {
    Scaffold {
        Column() {
            HeaderComponent(
                tittle = recipe.receita,
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_clipboard,
                onLeftClick = onLeftClick
            )

            Spacer(modifier = Modifier.height(22.dp))

        }
    }
}