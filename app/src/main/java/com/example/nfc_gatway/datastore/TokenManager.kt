package com.example.nfc_gatway.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import android.util.Base64
import org.json.JSONObject

object TokenManager {
    private const val PREFS_NAME = "user_prefs"
    private const val TOKEN_KEY = "auth_token"


    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }

    fun getEmployeeIdFromToken(context: Context): String {
        return try {
            val token = getToken(context) ?: return ""
            val payload = String(Base64.decode(token.split(".")[1], Base64.DEFAULT))
            val json = JSONObject(payload)
            json.getString("employeeId")
        } catch (e: Exception) {
            ""
        }
    }

    fun getEmployeeNameFromToken(context: Context): String {
        return try {
            val token = getToken(context) ?: return ""
            val payload = String(Base64.decode(token.split(".")[1], Base64.DEFAULT))
            val json = JSONObject(payload)
            json.getString("name")
        } catch (e: Exception) {
            ""
        }
    }
    fun getEmailFromToken(context: Context): String {
        val token = getToken(context) ?: return ""
        return try {
            val parts = token.split(".")
            if (parts.size == 3) {
                val decodedPayload = String(Base64.decode(parts[1], Base64.DEFAULT))
                val json = JSONObject(decodedPayload)
                json.getString("email") ?: ""
            } else ""
        } catch (e: Exception) {
            ""
        }
    }
}
