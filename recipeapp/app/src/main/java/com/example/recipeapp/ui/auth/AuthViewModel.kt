package com.example.recipeapp.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.dataStore.SessionManager
import com.example.recipeapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState (
    val user: FirebaseUser? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val success: String? = null
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
            _state.value = _state.value.copy(loading = true, error = null, success = null)
            try{
                repository.loginWithEmail(email, password)
                val user = repository.currentUser()

                if(user?.isEmailVerified == true) {
                    val expiration = if (remember)
                        System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 // 30 dias
                    else
                        System.currentTimeMillis() + 1000L // 1 segundo
                    sessionManager.saveExpiration(expiration)
                    _state.value = AuthState(user = user, loading = false)
                } else {
                    repository.signOut()
                    _state.value = AuthState(
                        user = null,
                        loading = false,
                        error = "Verifique seu e-mail antes de acessar."
                    )
                }
            } catch (e: Exception){
                _state.value = AuthState(user = null, loading = false, error = e.localizedMessage)
            }
        }
    }

    fun register(email: String, password: String, remember: Boolean){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null, success = null)
            try {
                repository.registerWithEmail(email, password)

                val user = repository.currentUser()

                user?.sendEmailVerification()?.addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        _state.value = AuthState(
                            user = null,
                            loading = false,
                            success = "E-mail de verificação enviado. Verifique sua caixa de entrada."
                        )
                    } else {
                        _state.value = AuthState(
                            user = null,
                            loading = false,
                            error = "Erro ao enviar e-mail de verificação."
                        )
                    }
                }
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

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                repository.loginWithGoogle(idToken)
                val user = repository.currentUser()

                if(user != null) {
                    val expiration = System.currentTimeMillis() + 1000L * 60 * 60 * 60
                    sessionManager.saveExpiration(expiration)
                    _state.value = _state.value.copy(user = user)
                } else {
                    _state.value = _state.value.copy(user = null)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    fun clearMessage() {
        _state.value = _state.value.copy(error = null, success = null)
    }
}
