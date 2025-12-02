package com.example.recipe_app.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    val id: Int,
    val receita: String,
    val ingredientes: String,
    @SerializedName("modo_preparo") val modoPreparo: String,
    @SerializedName("link_imagem") val linkImagem: String,
    val tipo: String
)

data class RecipeResponse(
    val data: List<Recipe>
)