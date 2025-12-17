package com.example.recipeapp.ui.screens.PefilScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.repository.ProfilePictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilScreenViewModel(
    private val repository: ProfilePictureRepository = ProfilePictureRepository()
): ViewModel() {
    private val _profilePicture = MutableStateFlow<String?>(null)
    val profilePicture = _profilePicture.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun clearError() { _error.value = null }
    fun addProfilePicture(uid: String, pictureUrl: String){
        viewModelScope.launch {
            _loading.value = true
            repository.addProfilePicture(uid, pictureUrl)
            _profilePicture.value = pictureUrl
            _loading.value = false
        }
    }

    fun removeProfilePicture(uid: String){
        viewModelScope.launch {
            _loading.value = true
            repository.removePicture(uid)
            _profilePicture.value = null
            _loading.value = false
        }
    }

    fun getProfilePicture(uid: String){
        viewModelScope.launch {
            _loading.value = true
            _profilePicture.value = repository.getPicture(uid)
            _loading.value = false
        }
    }

    fun uploadAndSavePicture(context: android.content.Context, uid: String, uri: Uri){
        viewModelScope.launch {
            _loading.value = true
            try {
                val uploadedUrl = repository.uploadToCloudinary(context, uri)
                if (uploadedUrl != null) {
                    addProfilePicture(uid, uploadedUrl)
                } else {
                    _error.value = "Falha ao enviar imagem para o servidor."
                }
            } catch (e: Exception) {
                _error.value = "Erro inesperado: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

}