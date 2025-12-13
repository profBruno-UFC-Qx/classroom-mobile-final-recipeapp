package com.example.recipeapp.ui.screens.PefilScreen


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent

@Composable
fun PerfilScreen(
    onLeftClick: () -> Unit
){
    Scaffold { padding ->
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