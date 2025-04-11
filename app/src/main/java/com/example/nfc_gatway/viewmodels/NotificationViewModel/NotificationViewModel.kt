package com.example.nfc_gatway.viewmodels.NotificationViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotificationViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _notifications = mutableStateListOf<Map<String, Any>>()
    val notifications: List<Map<String, Any>> get() = _notifications

    fun fetchEmployeeNotificationsByEmail(email: String) {
        db.collection("employeeNotifications")
            .whereEqualTo("email", email)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Notifications", "Fetch error: ${error.message}")
                    return@addSnapshotListener
                }

                snapshot?.let {
                    _notifications.clear()
                    for (doc in it.documents) {
                        _notifications.add(doc.data ?: emptyMap())
                    }
                }
            }
    }
}