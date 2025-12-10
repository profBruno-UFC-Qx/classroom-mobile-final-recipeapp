package com.example.recipeapp.ui.screens.RecipeDetailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.R
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onLeftClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Column( modifier = Modifier.padding(horizontal = 12.dp)) {
            HeaderComponent(
                tittle = "Detalhes Receita",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_clipboard,
                onLeftClick = onLeftClick
            )

            Spacer(modifier = Modifier.height(22.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
            ) {
                AsyncImage(
                        model = recipe.link_imagem,
                        contentDescription = recipe.receita,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onBackground)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            // Split ingredients
            val ingredientsList = recipe.ingredientes.split(",")
            Card(
                modifier = Modifier
                .fillMaxWidth()
                .padding(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    text = "Ingredientes:",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                ){
                    items(ingredientsList){ ingredient ->
                        Text(
                            text = "- ${ingredient}",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Left
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }
    }
}