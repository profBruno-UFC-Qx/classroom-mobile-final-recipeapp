package com.example.recipeapp.data.repository

import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.remote.ApiClient
import com.example.recipeapp.data.remote.RecipeService

class RecipeRepository {
    private val api = ApiClient.retrofit.create(RecipeService::class.java)

    suspend fun getRecipes(page: Int, limit: Int = 10): List<Recipe> {
        return api.getRecipes(page, limit).items
    }

    suspend fun getRecipe(id: Int): Recipe {
        return api.getRecipeById(id).items
    }

    suspend fun searchRecipes(name: String): List<Recipe> {
        return api.searchRecipe(name).items
    }

    suspend fun filterByType(type: String): List<Recipe> {
        return api.filterByType(type)
    }
}