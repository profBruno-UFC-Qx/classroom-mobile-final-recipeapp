package com.example.recipeapp.ui.screens.PefilScreen


import android.annotation.SuppressLint
import android.view.Display
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.ui.navigation.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: PerfilScreenViewModel = viewModel(),
    uid: String,
    userEmail: String,
    displayName: String
){
    val pictureUrl by viewModel.profilePicture.collectAsState()
    val loading by viewModel.loading.collectAsState()

    var showOptions by remember { mutableStateOf(false) }

    LaunchedEffect(uid) {
        viewModel.getProfilePicture(uid)
    }
    Scaffold(
        bottomBar = { BottomBar(navController as NavHostController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showOptions = true},
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Alterar foto de perfil")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderComponent(
                tittle = "Perfil",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_person,
                onLeftClick = {
                    navController.popBackStack()
                },
                isLogo = false
            )
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.size(220.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .border(width = 4.dp, color = MaterialTheme.colorScheme.tertiary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (loading){
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                } else if (pictureUrl != null){
                    AsyncImage(
                        model = pictureUrl,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_person_fill),
                        contentDescription = "Sem foto de perfil",
                        modifier = Modifier.size(120.dp).graphicsLayer(colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onPrimary, blendMode = BlendMode.SrcIn))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = displayName ?: "Usuário",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = userEmail ?: "Usuário",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        if(showOptions){
            ModalBottomSheet(
                onDismissRequest = {showOptions = false }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            // Tirar foto
                            showOptions = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                    ) {
                        Text(
                            "Tirar Foto",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                        onClick = {
                            // Escolher da galeria
                            showOptions = false
                        }
                    ) {
                        Text(
                            "Escolher da galeria",
                                color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.removeProfilePicture(uid)
                            showOptions = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(
                            "Remover foto",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}