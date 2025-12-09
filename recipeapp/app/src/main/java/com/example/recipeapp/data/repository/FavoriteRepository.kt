package com.example.recipeapp.data.repository

import com.example.recipeapp.data.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FavoriteRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun favoritesCollection(uid: String) =
        db.collection("users")
            .document(uid)
            .collection("favorites")

    suspend fun addFavorite(uid: String, recipe: Recipe){
        favoritesCollection(uid)
            .document()
            .set(recipe)
            .await()
    }

    suspend fun removeFavorite(uid: String, recipeId: String){
        favoritesCollection(uid)
            .document(recipeId)
            .delete()
            .await()
    }

    suspend fun getFavorites(uid: String): List<Recipe>{
        return try {
            val snapshot = favoritesCollection(uid).get().await()
            snapshot.documents.mapNotNull { it.toObject(Recipe::class.java) }

        } catch (e: Exception) {
            println("Erro na função" + e.message)
            emptyList()
        }
    }

}