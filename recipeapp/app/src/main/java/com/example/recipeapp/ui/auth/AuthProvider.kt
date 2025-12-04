package com.example.recipeapp.ui.auth

import androidx.compose.runtime.staticCompositionLocalOf
import com.google.firebase.auth.FirebaseUser

data class LocalAuth(
    val user: FirebaseUser?,
    val login: (String, String, Boolean) -> Unit,
    val register: (String, String) -> Unit,
    val logout: () -> Unit,
    val loginWithGoogle: () -> Unit
)

val localAuthState = staticCompositionLocalOf<LocalAuth> {
    error("LocalAuthState not provided")
}