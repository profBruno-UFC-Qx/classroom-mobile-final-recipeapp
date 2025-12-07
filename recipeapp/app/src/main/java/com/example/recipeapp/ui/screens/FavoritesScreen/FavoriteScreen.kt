package com.example.recipeapp.ui.screens.FavoritesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(onLeftClick: () -> Unit){
    Scaffold {
        Column() {
            HeaderComponent(
                tittle = "Favoritos",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_heart,
                onLeftClick = {
                    onLeftClick()
                },
                isLogo = false
            )
            Text(text = "favorite Screen")
        }
    }
}