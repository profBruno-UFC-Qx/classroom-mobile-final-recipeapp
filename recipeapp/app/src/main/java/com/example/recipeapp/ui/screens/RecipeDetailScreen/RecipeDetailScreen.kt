package com.example.recipeapp.ui.screens.RecipeDetailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.R
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onLeftClick: () -> Unit
) {
    Scaffold { padding ->
        Column( modifier = Modifier.padding(padding)) {
            HeaderComponent(
                tittle = "Detalhes Receita",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_clipboard,
                onLeftClick = onLeftClick
            )

            Spacer(modifier = Modifier.height(22.dp))

            LazyColumn (
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 22.dp)
            ){
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        SubcomposeAsyncImage(
                            model = recipe.link_imagem,
                            contentDescription = recipe.receita,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.onBackground),
                            error = {
                                Column(
                                    modifier = Modifier.fillMaxSize().background(Color.Gray),
                                    Arrangement.Center,
                                    Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_card_image),
                                        contentDescription = "Erro ao carregar imagem",
                                        modifier = Modifier.size(130.dp).graphicsLayer(colorFilter = ColorFilter.tint(
                                            MaterialTheme.colorScheme.onPrimary, blendMode = BlendMode.SrcIn
                                        ))
                                    )
                                    Spacer(Modifier.height(12.dp))
                                    Text(
                                        "Imagem não encontrada!",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }

                // Igredients
                val ingredientsList = recipe.ingredientes.split(",")
                item {
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
                        if(ingredientsList.isNotEmpty()){
                            Column(modifier = Modifier.padding(16.dp)) {
                                ingredientsList.forEach { ingredient ->
                                    Text(
                                        text = "- $ingredient",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                }
                            }
                        } else {
                            Text(
                                text = "Nenhum ingrediente encontrado.",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Modo de preparo
                val regex = """(?s)(\d\.\s*.*?)(?=\s*\d\.\s*|$)""".toRegex()
                val steps = regex.findAll(recipe.modo_preparo).map {it.value}.toList()

                item {
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
                            text = "Passo a passo:",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        if(steps.isNotEmpty()){
                            Column(modifier = Modifier.padding(16.dp)) {
                                steps.forEach { step ->
                                    Text(
                                        text = step,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                }
                            }
                        } else {
                            Text(
                                text = "Nenhum passo encontrado.",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}