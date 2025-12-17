package com.example.recipeapp.data.repository

import com.example.recipeapp.data.model.Recipe
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.userAgent


class ProfilePictureRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun profilePictureRef(uid: String) =
        db.collection("users")
            .document(uid)

    suspend fun addProfilePicture(uid: String, pictureUrl: String){
        try {
            val data = mapOf(
                "profilePhotoUrl" to pictureUrl
            )

            profilePictureRef(uid)
                .set(data, com.google.firebase.firestore.SetOptions.merge())
                .await()
        } catch (e: Exception){
            throw Exception("Erro ao adicionar foto de perfil.", e)
        }
    }

    suspend fun removePicture(uid: String){
        try {
            profilePictureRef(uid)
                .update("profilePhotoUrl", FieldValue.delete())
                .await()
        } catch (e: Exception){
            throw Exception("Erro ao remover foto de perfil.", e)
        }
    }

    suspend fun getPicture(uid: String): String? {
        return try {
            val snapshot = profilePictureRef(uid).get().await()

            if(!snapshot.exists()) return null
            snapshot.getString("profilePhotoUrl")
        } catch (e: Exception) {
            return null
        }
    }
}
