package com.example.recipeapp.ui.screens.ResetPasswordScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel(){
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

     fun sendEmail(email: String){
         viewModelScope.launch {
             try {
                 _loading.value = true
                 repository.resetPassword(email)
                 _success.value = true
             } catch (e: Exception){
                _error.value = e.message
             } finally {
                 _loading.value = false
             }
         }
    }

    fun clearSuccess() {
        _success.value = false
    }

    fun clearError() {
        _error.value = null
    }
}