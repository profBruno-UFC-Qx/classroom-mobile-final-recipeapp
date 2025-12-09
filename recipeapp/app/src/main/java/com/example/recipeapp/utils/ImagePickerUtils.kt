package com.example.recipeapp.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberGalleryPicker(
    onImageSelected: (Uri?) -> Unit
): () -> Unit {
    val laucher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri -> onImageSelected(uri)}
    )

    return {
        laucher.launch("image/")
    }
}

@Composable
fun rememberCameraPicker(
    onImageCaptured: (Uri?) -> Unit
): () -> Unit{
    val context = LocalContext.current
    val laucher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview(),
        onResult = {bitmap ->
            if(bitmap != null){
                val uri = ImageTempStorage.storeBitmap(bitmap, context)
                onImageCaptured(uri)
            } else {
                onImageCaptured(null)
            }
        }
    )

    return {laucher.launch(null)}
}