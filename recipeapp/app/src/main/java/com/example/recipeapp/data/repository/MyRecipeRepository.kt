package com.example.recipeapp.data.repository

import com.example.recipeapp.data.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MyRecipeRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun myRecipesCollection(uid: String) =
        db.collection("users")
            .document(uid)
            .collection("recipes")

    suspend fun addRecipe(uid: String, recipe: Recipe, recipeId: String){
        myRecipesCollection(uid)
            .document(recipeId)
            .set(recipe)
            .await()
    }

    suspend fun removeRecipe(uid: String, recipeId: String){
        myRecipesCollection(uid)
            .document(recipeId)
            .delete()
            .await()
    }

    suspend fun getRecipes(uid: String): List<Recipe>{
        return try{
            val snapshot = myRecipesCollection(uid).get().await()
            snapshot.documents.mapNotNull { it.toObject(Recipe::class.java) }
        } catch (e: Exception){
            println("Erro ao buscar suas receitas ${e.message}")
            emptyList()
        }
    }
}