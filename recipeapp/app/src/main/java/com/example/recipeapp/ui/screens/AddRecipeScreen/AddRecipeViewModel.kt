package com.example.recipeapp.ui.screens.AddRecipeScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddRecipeViewModel(): ViewModel(){
    val _recipeImage = MutableStateFlow<Uri?>(null)
    val recipeImage = _recipeImage.asStateFlow()
    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun onImageSelected(uri: Uri?){
        _recipeImage.value = uri
    }

    fun removeImage(){
        _recipeImage.value = null
    }
}