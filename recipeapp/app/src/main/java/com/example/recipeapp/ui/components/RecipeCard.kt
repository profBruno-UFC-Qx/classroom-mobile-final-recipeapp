package com.example.recipeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.screens.FavoritesScreen.FavoritesViewModel

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    uid: String,
    viewModel: FavoritesViewModel,
) {
    val favorites by viewModel.favorites.collectAsState()
    val isFavorite = favorites.any {it.id == recipe.id}


    fun onToggleFavorite(){
        viewModel.onToggleFavorites(uid, recipe)
        viewModel.loadFavorites(uid)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClick()}
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ){
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth()){
                SubcomposeAsyncImage(
                    model = recipe.link_imagem,
                    contentDescription = recipe.receita,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
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
                    val image = if(isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart
                    val description = if (isFavorite) "Remover favorito" else "Adicionar aos favoritos"

                Image(
                    painter = painterResource(image),
                    contentDescription = description,
                    modifier =  Modifier.clickable(onClick = {onToggleFavorite()})
                        .graphicsLayer(
                        colorFilter = ColorFilter.tint(
                            color = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                            BlendMode.SrcIn
                            )
                        )
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = recipe.receita,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = recipe.ingredientes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}