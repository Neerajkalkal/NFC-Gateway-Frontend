package com.example.nfc_gatway.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object TokenManager {
    private const val DATASTORE_NAME = "NFC-Gateway"
    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

    private val TOKEN_KEY = stringPreferencesKey("4b4777ffbdb6db25ffd034537261e17a5c01fbaeeec920b10e37e104fb975a7e")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(context: Context): String? {
        val preferences = context.dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    suspend fun clearToken(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}
