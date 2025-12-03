package com.example.recipeapp.data.auth

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context){
    private val prefs: SharedPreferences = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SESSION_EXPIRATION = "session_expiration"
    }

    fun saveExpiration(expirationMilli: Long){
        prefs.edit().putLong(KEY_SESSION_EXPIRATION, expirationMilli).apply()
    }

    fun getExpiration(): Long = prefs.getLong(KEY_SESSION_EXPIRATION, 0L)

    fun clear() {
        prefs.edit().remove(KEY_SESSION_EXPIRATION).apply()
    }

    fun isSessionExpired(): Boolean {
        val exp = getExpiration()
        if(exp == 0L) return true
        return System.currentTimeMillis() > exp
    }
}