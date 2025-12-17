package com.example.recipeapp.ui.screens.PefilScreen

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
}