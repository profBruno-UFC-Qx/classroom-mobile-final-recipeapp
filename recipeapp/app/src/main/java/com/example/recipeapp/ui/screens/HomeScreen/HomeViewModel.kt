package com.example.recipeapp.ui.screens.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeState(
    val loading: Boolean = false,
    val error: String? = null,
    val recipes: List<Recipe> = emptyList()
)
class HomeViewModel: ViewModel() {
    private val repository = RecipeRepository()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        loadRecipes()
    }
    fun loadRecipes() {
        viewModelScope.launch {
            _state.value = HomeState(loading = true)

            try {
                val result = repository.getRecipes(page = 1, limit = 20)
                _state.value = HomeState(recipes = result)
            } catch (e: Exception) {
                _state.value = HomeState(error = e.localizedMessage)
            }
        }
    }
}