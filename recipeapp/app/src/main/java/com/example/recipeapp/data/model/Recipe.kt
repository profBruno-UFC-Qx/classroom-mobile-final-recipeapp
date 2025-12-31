package com.example.recipeapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe (
    val id: String = "",
    val receita: String = "",
    val ingredientes: String = "",
    val modo_preparo: String = "",
    val link_imagem: String = "",
    val tipo: String = ""
) : Parcelable