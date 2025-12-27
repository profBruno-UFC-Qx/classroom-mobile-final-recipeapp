package com.example.recipeapp.ui.screens.MyRecipesScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.MyRecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MyRecipeViewModel (
    private val repository: MyRecipeRepository = MyRecipeRepository()
): ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    suspend fun loadRecipes(uid: String){
        _loading.value = true
        try {
            val result = repository.getRecipes(uid)
            _recipes.value = result
        } catch (e: Exception) {
            _recipes.value = emptyList()
        } finally {
            _loading.value = false
        }
    }
}