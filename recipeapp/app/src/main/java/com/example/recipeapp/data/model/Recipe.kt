package com.example.recipeapp.data.model

import com.google.gson.annotations.SerializedName

data class Recipe (
    val id: String = "",
    val receita: String = "",
    val ingredientes: String = "",
    val modo_preparo: String = "",
    val link_imagem: String = "",
    val tipo: String = ""
)