package com.example.recipeapp.ui.screens.PefilScreen

import android.content.Context
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

    val _pendingImageUri = MutableStateFlow<Uri?>(null)
    val pendingUri = _pendingImageUri.asStateFlow()
    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun clearError() { _error.value = null }
    
    fun onImageSelected(uri: Uri?){
        _pendingImageUri.value = uri
    }

    fun cancelUpload(){
        _pendingImageUri.value = null
    }

    fun confirmUpload(context: Context, uid: String){
        val uriToUpload = _pendingImageUri.value ?: return

        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.uploadToCloudinary(context, uriToUpload)

                result.fold(
                    onSuccess = { uploadedUri ->
                        repository.addProfilePicture(uid, uploadedUri)
                        _profilePicture.value = uploadedUri
                        _pendingImageUri.value = null
                    },
                    onFailure = {exception ->
                        _error.value = "Falha no upload: ${exception.message}"
                    }
                )
            } catch (e: Exception){
                _error.value = "Erro: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
    fun removeProfilePicture(uid: String){
        viewModelScope.launch {
            _loading.value = true
            repository.removePicture(uid)
            _profilePicture.value = null
            _pendingImageUri.value = null
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

}