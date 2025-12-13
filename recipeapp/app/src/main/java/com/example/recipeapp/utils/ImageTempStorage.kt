package com.example.recipeapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageTempStorage {

    fun storeBitmap(bitmap: Bitmap, context: Context): Uri {
        val file = File(context.cacheDir, "camera_${System.currentTimeMillis()}.jpg")
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.close()
        return Uri.fromFile(file)
    }
}
