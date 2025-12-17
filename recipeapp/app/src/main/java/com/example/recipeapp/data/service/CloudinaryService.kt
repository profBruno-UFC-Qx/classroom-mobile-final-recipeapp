package com.example.recipeapp.data.service

import com.example.recipeapp.BuildConfig
import android.content.Context
import android.net.Uri
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.InputStream

object CloudinaryService {
    private val cloudName = BuildConfig.CLOUDINARY_CLOUD_NAME
    private val cloudPresent = BuildConfig.CLOUDINARY_UPLOAD_PRESET
    private val uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

    private val client = OkHttpClient()

    suspend fun uploadImage(context: Context, imageUri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val base64 = uriToBase64(context, imageUri)

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", base64)
                    .addFormDataPart("upload_preset", cloudPresent)
                    .build()

                val request = Request.Builder()
                    .url(uploadUrl)
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                val json = response.body?.string()

                if(!response.isSuccessful || json == null) {
                    println("Erro de Upload, Falha ao subir a imagem para o servidor.")
                    return@withContext null
                }

                val secureUrl = Regex("\"secure_url\":\"(.*?)\"")
                    .find(json)?.groupValues?.get(1)

                secureUrl?.replace("\\/", "/")
            } catch (e: Exception){
                println("Erro ao fazer upload da imagem para o Cloudinary:"+ e.message)
                null
            }
        }
    }

    private fun uriToBase64(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return ""

        val base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
        return "data:image/jpeg;base64,$base64"
    }

}