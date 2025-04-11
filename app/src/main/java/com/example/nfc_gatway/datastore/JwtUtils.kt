package com.example.nfc_gatway.datastore


import android.util.Base64
import org.json.JSONObject

fun decodeJwtToken(token: String): Map<String, Any>? {
    return try {
        val parts = token.split(".")
        if (parts.size == 3) {
            val decoded = Base64.decode(parts[1], Base64.DEFAULT)
            val json = JSONObject(String(decoded))
            json.keys().asSequence().associateWith { key -> json.get(key) }
        } else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}