package com.example.recipeapp.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.sessionDataStore by preferencesDataStore(
    name = "session_prefs"
)
class SessionManager(private val context: Context){

    companion object {
        private val KEY_SESSION_EXPIRATION = longPreferencesKey("session_expiration")
    }

    suspend fun saveExpiration(expirationMilli: Long){
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_SESSION_EXPIRATION] = expirationMilli
        }
    }

    suspend fun getExpiration(): Long{
        return context.sessionDataStore.data.map { prefs ->
            prefs[KEY_SESSION_EXPIRATION] ?: 0L
        }.first()
    }

    suspend fun clear() {
        context.sessionDataStore.edit { prefs ->
            prefs.remove(KEY_SESSION_EXPIRATION)
        }
    }

    suspend fun isSessionExpired(): Boolean {
        val expiration = getExpiration()
        if(expiration == 0L) return true
        return System.currentTimeMillis() > expiration
    }

}