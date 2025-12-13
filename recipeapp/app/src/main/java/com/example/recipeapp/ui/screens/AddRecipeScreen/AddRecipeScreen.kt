package com.example.recipeapp.ui.screens.AddRecipeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.R
@Composable
fun AddRecipeScreen(
    onLeftClick: () -> Unit
){
    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            HeaderComponent(
                onLeftClick = {onLeftClick()},
                tittle = "Adicionar Receita",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_plus_circle
            )
            Text(
                "Adicionar receita",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}