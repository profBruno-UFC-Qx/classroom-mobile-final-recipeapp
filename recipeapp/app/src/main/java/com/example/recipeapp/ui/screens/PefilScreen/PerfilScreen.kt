package com.example.recipeapp.ui.screens.PefilScreen


import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.ui.navigation.BottomBar
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: PerfilScreenViewModel = viewModel(),
    uid: String,
    userEmail: String,
    displayName: String
){
    val context = LocalContext.current

    val pictureUrl by viewModel.profilePicture.collectAsState()
    val pendingUri by viewModel.pendingUri.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showOptions by remember { mutableStateOf(false) }

    // Errors alerts

    var showErrorAlert by remember {mutableStateOf(false)}
    var errorMessage by remember { mutableStateOf("") }

    fun triggerError(msg: String){
        errorMessage = msg
        showErrorAlert = true
    }

    // Get Profile Picture
    LaunchedEffect(uid) {
        viewModel.getProfilePicture(uid)
    }
    // get error
    LaunchedEffect(error) {
        error?.let {triggerError(it); viewModel.clearError()}
    }

    // Pictures functions

    val tempUri = remember {
        val directory = File(context.externalCacheDir, "camera")
        if (!directory.exists()) directory.mkdirs()
        val file = File(directory, "profile_tmp.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        uri.let { viewModel.onImageSelected(it)}
    }

    val cameraLaucher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { sucess ->
        if(sucess) viewModel.onImageSelected(tempUri)
    }

    // Permissions
    val permissionTakePictureLaucher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGrated ->
        if(isGrated) cameraLaucher.launch(tempUri) else triggerError("Permissão negada")
    }

    val permissionGetInLibraryLaucher = rememberLauncherForActivityResult((ActivityResultContracts.RequestPermission())) { isGrated ->
        if(isGrated) galleryLauncher.launch("image/*")
    }

    Scaffold(
        bottomBar = { BottomBar(navController as NavHostController) },
        floatingActionButton = {
                if(pendingUri == null){
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

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(220.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .border(width = 4.dp, color = MaterialTheme.colorScheme.tertiary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (loading){
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    } else {
                        val modelToRender = pendingUri ?: pictureUrl

                        if(modelToRender != null){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(modelToRender)
                                    .crossfade(true)
                                    .transformations(CircleCropTransformation())
                                    .build(),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxWidth().clip(CircleShape)
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
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Se existir uma imagem pendente mostra os botões de salvar ou cancelar alterações
                if(pendingUri != null && !loading){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Deseja salvar as alterações?",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {viewModel.confirmUpload(context, uid)},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                            ){
                                Text(
                                    "Salvar",
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Button(
                                onClick = {viewModel.cancelUpload()},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ){
                                Text(
                                    "Cancelar",
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                Text(
                    text = displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
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
                            permissionTakePictureLaucher.launch(Manifest.permission.CAMERA)
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
                            permissionGetInLibraryLaucher.launch(Manifest.permission.CAMERA)
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

        if(showErrorAlert){
            AlertDialog(
                onDismissRequest = {showErrorAlert = false},
                confirmButton = {
                    Button(onClick = {
                        showErrorAlert = false
                    }) {
                        Text("Ok")
                    }
                },
                title = {Text("Atenção")},
                text = {Text(errorMessage)}
            )
        }
    }
}