package com.example.recipeapp.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.auth.SessionManager
import com.example.recipeapp.data.repository.AuthRepository
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState (
    val user: FirebaseUser? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class AuthViewModel (
    application: Application,
    private val repository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager = SessionManager(application),
): AndroidViewModel(application) {
    private val _state = MutableStateFlow(AuthState(user = repository.currentUser()))
    val state: StateFlow<AuthState> = _state

    init {
        viewModelScope.launch {
            if(sessionManager.isSessionExpired()){
                repository.signOut()
                sessionManager.clear()
                _state.value = AuthState(user = null)
            } else {
                _state.value = AuthState(user = repository.currentUser())
            }
        }
    }

    fun login(email: String, password: String, remember: Boolean = false){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try{
                repository.loginWithEmail(email, password)
                val expiration = if (remember)
                    System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 // 30 dias
                else
                    System.currentTimeMillis() + 1000L // 1 segundo
                sessionManager.saveExpiration(expiration)
                _state.value = AuthState(user = repository.currentUser(), loading = false)
            } catch (e: Exception){
                _state.value = AuthState(user = null, loading = false, error = e.localizedMessage)
            }
        }
    }

    fun register(email: String, password: String, remember: Boolean){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                repository.registerWithEmail(email, password)
                val expiration = if (remember)
                    System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 // 30 dias
                else
                    System.currentTimeMillis() + 1000L // 1 segundo
                sessionManager.saveExpiration(expiration)
                _state.value = AuthState(user = repository.currentUser(), loading = false)
            } catch (e: Exception) {
                _state.value = AuthState(user = null, )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.signOut()
            sessionManager.clear()
            _state.value = AuthState(user = null)
        }
    }

    fun resetPassword(email: String, onComplete: (Result<Unit>) -> Unit){
        viewModelScope.launch {
            try {
                repository.resetPassword(email)
                onComplete(Result.success(Unit))
            } catch (e: Exception) {
                onComplete(Result.failure(e))
            }
        }
    }
}
