package com.example.recipeapp.ui.screens.AddRecipeScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.MyRecipeRepository
import com.example.recipeapp.data.repository.ProfilePictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val repository: MyRecipeRepository = MyRecipeRepository(),
): ViewModel(){
    val _recipeImage = MutableStateFlow<Uri?>(null)
    val recipeImage = _recipeImage.asStateFlow()
    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    private val _sucess = MutableStateFlow(false)
    val sucess = _sucess.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    fun onImageSelected(uri: Uri?){
        _recipeImage.value = uri
    }

    fun removeImage(){
        _recipeImage.value = null
    }

    fun createRecipe(
        context: Context,
        uid: String,
        recipeName: String,
        type: String,
        ingredients: List<String>,
        instructions: List<String>
    ) {
        val uri = _recipeImage.value ?: return

        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.uploadToCloudinary(context, uri)

                result.fold(
                    onSuccess = { imageUrl ->
                        val recipeId = repository.generateRecipeId(uid)

                        val ingredientsString =
                            ingredients.joinToString(separator = "\n")

                        val instructionsString =
                            instructions.mapIndexed { index, instruction ->
                                "${index + 1}. $instruction"
                            }.joinToString(separator = "\n")

                        val recipe = Recipe (
                            id = recipeId,
                            receita = recipeName,
                            ingredientes = ingredientsString,
                            modo_preparo = instructionsString,
                            link_imagem = imageUrl,
                            tipo = type
                        )

                        repository.addRecipe(uid, recipe, recipeId)
                        _sucess.value = true
                    },
                    onFailure = {
                        _error.value = "Erro ao enviar imagem"
                    }
                )
            } catch (e: Exception) {
                _error.value = "Erro: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearSucess() {
        _sucess.value = false
    }
}