package com.example.nfc_gatway.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object TokenManager {
    private const val PREFS_NAME = "user_prefs"
    private const val TOKEN_KEY = "auth_token"
//    private const val DATASTORE_NAME = "NFC-Gateway"
//    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

//    private val TOKEN_KEY = stringPreferencesKey("4b4777ffbdb6db25ffd034537261e17a5c01fbaeeec920b10e37e104fb975a7e")

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

//    suspend fun clearToken(context: Context) {
//        context.dataStore.edit { preferences ->
//            preferences.remove(TOKEN_KEY)
//        }
//    }
}
