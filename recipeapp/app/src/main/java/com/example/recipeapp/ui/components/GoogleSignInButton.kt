package com.example.recipeapp.ui.auth.components

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.recipeapp.R // Importe seu R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInButton(
    onGoogleSignInCompleted: (String) -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { token ->
                    onGoogleSignInCompleted(token)
                } ?: onError("Token ID não encontrado")
            } catch (e: ApiException) {
                onError("Erro Google: ${e.statusCode}")
            }
        }
    }

    Button(
        onClick = {
            val signInIntent = getGoogleSignInIntent(context)
            launcher.launch(signInIntent)
        }
    ) {
        Image(
            painter = painterResource(R.drawable.google_icon),
            contentDescription = "Faça login com google"
        )
    }
}

private fun getGoogleSignInIntent(context: Context): android.content.Intent {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    return googleSignInClient.signInIntent
}