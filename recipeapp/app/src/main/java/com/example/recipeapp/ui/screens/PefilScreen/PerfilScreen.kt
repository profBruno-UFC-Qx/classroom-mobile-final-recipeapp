package com.example.recipeapp.ui.screens.PefilScreen


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.ui.navigation.BottomBar

@Composable
fun PerfilScreen(
    onLeftClick: () -> Unit,
    navController: NavController
){
    Scaffold(
        bottomBar = { BottomBar(navController as NavHostController) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            HeaderComponent(
                tittle = "Perfil",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_person,
                onLeftClick = {
                    onLeftClick()
                },
                isLogo = false
            )
            Text(text = "Perfil Screen")
        }
    }
}