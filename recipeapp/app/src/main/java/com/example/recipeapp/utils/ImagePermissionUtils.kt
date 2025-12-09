package com.example.recipeapp.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun useCameraPermission(onGrated: () -> Unit): () -> Unit {
    val context = LocalContext.current

    val shouldRequest by remember {mutableStateOf(false)}

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { grated ->
        if(grated) onGrated()
    }

    return {
        val status = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )

        if(status == PackageManager.PERMISSION_GRANTED){
            onGrated()
        } else {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }
}