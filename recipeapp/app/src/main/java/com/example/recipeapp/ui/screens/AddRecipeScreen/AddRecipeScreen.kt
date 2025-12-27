package com.example.recipeapp.ui.screens.AddRecipeScreen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.recipeapp.ui.components.HeaderComponent
import com.example.recipeapp.R
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    onLeftClick: () -> Unit,
    viewModel: AddRecipeViewModel = viewModel(),
    uid: String
){
    // Context
    val context = LocalContext.current

    // View Model states

    val recipeImage by viewModel.recipeImage.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Recipe name
    var recipeName by remember { mutableStateOf<String>("") }
    // Types
    var type by remember { mutableStateOf<String?>(null) }

    // Ingredients States

    var ingredients by remember { mutableStateOf(listOf<String>()) }
    var showIngredientModal by remember { mutableStateOf(false) }
    var tempIngredients by remember { mutableStateOf(listOf("")) }

    // instructions state
    var instructions by remember { mutableStateOf(listOf<String>()) }
    var showInstructionModal by remember { mutableStateOf(false) }
    var tempInstructions by remember { mutableStateOf(listOf("")) }

    // Show options button

    var showOptions by remember { mutableStateOf(false) }

    // Sucess Update
    val sucess by viewModel.sucess.collectAsState()
    var showSucessAlert by remember { mutableStateOf(false) }
    var sucessMessage by remember { mutableStateOf("") }

    fun triggerSucess(msg: String) {
        sucessMessage = msg
        showSucessAlert = true
    }

    if (sucess) {
        triggerSucess("Receita criada com sucesso")

        // Clear fields
        recipeName = ""
        type = null
        ingredients = emptyList()
        instructions = emptyList()
        tempIngredients = listOf("")
        tempInstructions = listOf("")
        viewModel.removeImage()

        viewModel.clearSucess()
    }
    
    // Errors alerts

    var showErrorAlert by remember {mutableStateOf(false)}
    var errorMessage by remember { mutableStateOf("") }

    fun triggerError(msg: String){
        errorMessage = msg
        showErrorAlert = true
    }

    // image functions

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

    // Scroll State
    val scrollState = rememberScrollState()
    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).verticalScroll(scrollState)
        ) {
            HeaderComponent(
                onLeftClick = {onLeftClick()},
                tittle = "Adicionar Receita",
                leftIcon = R.drawable.ic_left_arrow,
                rightIcon = R.drawable.ic_plus_circle
            )

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Image Card
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 240.dp, max = 300.dp)
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (recipeImage == null) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_card_image),
                                    contentDescription = "Adicione uma imagem",
                                    modifier = Modifier.size(150.dp)
                                        .graphicsLayer(
                                            colorFilter = ColorFilter.tint(
                                                MaterialTheme.colorScheme.onPrimary,
                                                blendMode = BlendMode.SrcIn
                                            )
                                        )
                                )
                                Spacer(Modifier.height(22.dp))
                                Text(
                                    "Imagem da receita",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 28.sp
                                )
                            }

                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(recipeImage)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Imagem da receita",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        // Button edit image
                        FloatingActionButton(
                            onClick = { showOptions = true },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .size(50.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                            shape = CircleShape
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_pencil_fill),
                                contentDescription = "Editar imagem",
                                modifier = Modifier.size(26.dp)
                                    .graphicsLayer(
                                    colorFilter = ColorFilter.tint(
                                    MaterialTheme.colorScheme.tertiary,
                                    blendMode = BlendMode.SrcIn
                                ))
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Recipe name
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = {
                        recipeName = it
                    },
                    label = { Text("Digite um nome para a receita", color = MaterialTheme.colorScheme.onPrimary) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                )

                Spacer(Modifier.height(24.dp))

                // Select Type

                RecipeTypeSelector(
                    selectedType = type,
                    onTypeSelected = { type = it }
                )

                Spacer(Modifier.height(24.dp))

                // Ingredients card
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 300.dp)
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Column {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Ingredientes:",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 24.sp
                            )

                            TextButton(onClick = {showIngredientModal = true}) {
                                Row(
                                    modifier = Modifier.size(width = 200.dp, height = 40.dp)
                                        .background(MaterialTheme.colorScheme.background)
                                        .clip(RoundedCornerShape(12.dp)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "Adicionar Ingrediente",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }

                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        if(ingredients.isNotEmpty()){
                            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                                ingredients.forEachIndexed { index, item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text (
                                            "- $item",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontSize = 22.sp
                                        )
                                        Icon (
                                            painter = painterResource(R.drawable.ic_delete),
                                            contentDescription = "Deletar ingrediente",
                                            tint = Color.Red,
                                            modifier = Modifier.clickable{
                                                ingredients = ingredients.filterIndexed { i, _ -> i != index }
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if(showIngredientModal){
                            IngredientModal(
                                tempIngredients = tempIngredients,
                                onChange = {tempIngredients = it},
                                onDismiss = {
                                    showIngredientModal = false
                                    tempIngredients = listOf("")
                                },
                                onSave = {
                                    ingredients = ingredients + tempIngredients.filter { it.isNotBlank() }
                                    tempIngredients = listOf("")
                                    showIngredientModal = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Instructions card
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 300.dp)
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Column {
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Instruções:",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 24.sp
                            )

                            TextButton(onClick = {showInstructionModal = true}) {
                                Row(
                                    modifier = Modifier.size(width = 200.dp, height = 40.dp)
                                        .background(MaterialTheme.colorScheme.background)
                                        .clip(RoundedCornerShape(12.dp)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "Adicionar instrução",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }

                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        if(instructions.isNotEmpty()){
                            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                                instructions.forEachIndexed { index, item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text (
                                            "${index + 1}. $item",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontSize = 22.sp
                                        )
                                        Icon (
                                            painter = painterResource(R.drawable.ic_delete),
                                            contentDescription = "Deletar ingrediente",
                                            tint = Color.Red,
                                            modifier = Modifier.clickable{
                                                instructions = instructions.filterIndexed { i, _ -> i != index }
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        if(showInstructionModal){
                            InstructionModal(
                                tempInstructions = tempInstructions,
                                onChange = {tempInstructions = it},
                                onDismiss = {
                                    showInstructionModal = false
                                    tempInstructions = listOf("")
                                },
                                onSave = {
                                    instructions = instructions + tempInstructions.filter { it.isNotBlank() }
                                    tempInstructions = listOf("")
                                    showInstructionModal = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = {viewModel.createRecipe(
                        context = context,
                        uid = uid,
                        recipeName = recipeName,
                        type = type!!,
                        ingredients = ingredients,
                        instructions = instructions
                    )},
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp).height(45.dp),
                    enabled = if(instructions.isEmpty() || ingredients.isEmpty() || recipeImage == null || type == "") false else true
                ) {
                    if(loading){
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(25.dp)
                        )
                    } else {
                        Text(
                            "Adicionar receita",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))
            }

            // Image options
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
                                viewModel.removeImage()
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

            // Alert Erro PopUp
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

            // Alert Sucess PopUp
            if(showSucessAlert) {
                AlertDialog(
                    onDismissRequest = {showSucessAlert = false},
                    confirmButton = {
                        Button(onClick = {
                            showSucessAlert = false
                        }) {
                            Text("Ok")
                        }
                    },
                    title = {Text("Sucesso")},
                    text = {Text(sucessMessage)}
                )
            }
        }
    }
}

// Type Select Component2
@Composable
fun RecipeTypeSelector(
    selectedType: String?,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val items = listOf("Agridoce", "Doce", "Salgado")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedType ?: "Selecione o tipo da receita...",
                    color = if (selectedType == null){
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Abrir seletor"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            items.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = type,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
// Ingredient modal component
@Composable
fun IngredientModal(
    tempIngredients: List<String>,
    onChange: (List<String>) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onSave) {
                Text("Salvar Ingredientes", color = MaterialTheme.colorScheme.tertiary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Adicionar Ingredientes") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                tempIngredients.forEachIndexed { index, value ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = value,
                            onValueChange = {
                                val newList = tempIngredients.toMutableList()
                                newList[index] = it
                                onChange(newList)
                            },
                            label = { Text("Ingrediente ${index + 1}") },
                            modifier = Modifier.weight(1f)
                        )

                        if (tempIngredients.size > 1) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = "Remover",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .clickable {
                                        onChange(
                                            tempIngredients.filterIndexed { i, _ -> i != index }
                                        )
                                    }
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }

                TextButton(
                    onClick = { onChange(tempIngredients + "") }
                ) {
                    Text("Adicionar mais um ingrediente")
                }
            }
        }
    )
}

// instruction modal component
@Composable
fun InstructionModal(
    tempInstructions: List<String>,
    onChange: (List<String>) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onSave) {
                Text("Salvar Instruções", color = MaterialTheme.colorScheme.tertiary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Adicionar Instruções") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                tempInstructions.forEachIndexed { index, value ->
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        OutlinedTextField(
                            value = value,
                            onValueChange = {
                                val newList = tempInstructions.toMutableList()
                                newList[index] = it
                                onChange(newList)
                            },
                            label = { Text("Passo ${index + 1}") },
                            modifier = Modifier.weight(1f),
                            minLines = 2
                        )

                        if (tempInstructions.size > 1) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = "Remover",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .clickable {
                                        onChange(
                                            tempInstructions.filterIndexed { i, _ -> i != index }
                                        )
                                    }
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }

                TextButton(
                    onClick = { onChange(tempInstructions + "") }
                ) {
                    Text("Adicionar mais um passo")
                }
            }
        }
    )
}
