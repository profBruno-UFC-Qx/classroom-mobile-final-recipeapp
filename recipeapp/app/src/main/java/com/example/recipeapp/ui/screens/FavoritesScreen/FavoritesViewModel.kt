package com.example.recipeapp.ui.screens.FavoritesScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoritesViewModel: ViewModel () {
    private val repository = FavoriteRepository()

    private val _favorites = MutableStateFlow<List<Recipe>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    fun loadFavorites(uid: String){
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getFavorites(uid)
                Log.d("FavoritesViewModel", "Favoritos recebidos: $result")
                _favorites.value = result
            } catch (e: Exception) {
                Log.e("FavoritesViewModel", "Erro ao carregar favoritos", e)
                _favorites.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun addFavorite(uid: String, recipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.addFavorite(uid, recipe)
                loadFavorites(uid)
            } catch (e: Exception) {
                Log.e("FavoritesViewModel", "Erro ao adicionar favorito", e)
            }
        }
    }

    fun removeFavorite(uid: String, id: String) {
        viewModelScope.launch {
            try {
                repository.removeFavorite(uid, id)
                loadFavorites(uid)
            } catch (e: Exception) {
                Log.e("FavoritesViewModel", "Erro ao remover favorito", e)
            }
        }
    }

}