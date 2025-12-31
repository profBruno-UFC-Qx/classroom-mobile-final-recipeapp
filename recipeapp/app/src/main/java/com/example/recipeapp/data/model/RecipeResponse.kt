package com.example.recipeapp.data.model

data class RecipeListResponse(
    val items: List<Recipe>
)

data class RecipeItemReponse(
    val items: Recipe
)