package com.example.recipeapp.data.service

import android.content.Context
import android.net.Uri
import com.example.recipeapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.InputStream

object CloudinaryService {
    private val cloudName = "db0watb2b"
    private val cloudPreset = "recipesImages"
    private val uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

    private val client = OkHttpClient()

    suspend fun uploadImage(context: Context, imageUri: Uri): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                val bytes = inputStream?.readBytes()
                    ?: return@withContext Result.failure(Exception("Não foi possível ler o arquivo de imagem."))

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        "profile.jpg",
                        bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
                    )
                    .addFormDataPart("upload_preset", cloudPreset) // O PRESET DEVE SER 'UNSIGNED'
                    .build()

                val request = Request.Builder()
                    .url(uploadUrl)
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    val jsonResponse = response.body?.string()

                    if (!response.isSuccessful || jsonResponse == null) {
                        return@withContext Result.failure(Exception("Erro ${response.code}: $jsonResponse"))
                    }

                    val jsonObject = JSONObject(jsonResponse)
                    val url = jsonObject.optString("secure_url", "")
                    if (url.isNotEmpty()) {
                        Result.success(url)
                    } else {
                        Result.failure(Exception("URL não encontrada na resposta."))
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}