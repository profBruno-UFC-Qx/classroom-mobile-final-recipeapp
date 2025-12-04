package com.example.recipeapp.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()){
    suspend fun loginWithEmail(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun registerWithEmail(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun resetPassword(email: String): Void? {
        return auth.sendPasswordResetEmail(email).await()
    }

    fun currentUser() = auth.currentUser

    suspend fun signOut(){
        auth.signOut()
    }

    suspend fun loginWithGoogleIdToken(idToken: String): AuthResult{
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential).await()
    }

}