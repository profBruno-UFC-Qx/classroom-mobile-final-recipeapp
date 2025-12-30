package com.example.recipeapp.data.remote

import com.example.recipeapp.data.model.Recipe
import com.example.recipeapp.data.model.RecipeItemReponse
import com.example.recipeapp.data.model.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    // /receitas/todas?page=X&limit=10
    @GET("receitas/todas")
    suspend fun getRecipes(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ) : RecipeListResponse

    // /receitas/{id}
    @GET("receitas/{id}")
    suspend fun getRecipeById(
        @Path("id") id: Int
    ): RecipeItemReponse

    // /receitas/descricao?descricao=bolo
    @GET("receitas/descricao")
    suspend fun searchRecipe(
        @Query("descricao") name: String
    ): RecipeListResponse

    @GET("/receitas/tipo/{type}")
    suspend fun filterByType(
        @Path("type") type: String
    ): List<Recipe>
}