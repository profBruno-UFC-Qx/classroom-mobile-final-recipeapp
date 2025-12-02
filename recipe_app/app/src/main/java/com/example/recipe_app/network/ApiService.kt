package com.example.recipe_app.network

import com.example.recipe_app.model.Recipe
import com.example.recipe_app.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    // Pega todas as receitas
    @GET("receitas/todas")
    suspend fun getRecipes(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): RecipeResponse

    // Procura Receita por id
    @GET("receitas/{id}")
    suspend fun getRecipeDetail(@Path("id") id: Int): Recipe
}