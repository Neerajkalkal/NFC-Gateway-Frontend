package com.example.nfc_gatway.viewmodels.AdminNotificationViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.nfc_gatway.datastore.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.Timestamp
import org.json.JSONObject

class AdminNotificationViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _notifications = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val notifications: StateFlow<List<Map<String, Any>>> = _notifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        db.collection("adminNotifications")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) {
                    Log.e("AdminNotifications", "Error fetching: ${e?.message}")
                    return@addSnapshotListener
                }

                val notes = snapshot.documents.mapNotNull { doc ->
                    val data = doc.data ?: return@mapNotNull null
                    data["requestId"] = doc.id

                    // Format timestamps
                    data["startDate"] = (data["startDate"] as? Timestamp)?.toDate()?.toString() ?: "N/A"
                    data["endDate"] = (data["endDate"] as? Timestamp)?.toDate()?.toString() ?: "N/A"

                    data
                }

                _notifications.value = notes
            }
    }

    fun updateHolidayStatus(
        requestId: String,
        newStatus: String,
        employeeEmail: String, // 🔁 changed from employeeId
        message: String,
        context: Context
    ) {
        val requestRef = db.collection("holidayRequests").document(requestId)

        requestRef.get()
            .addOnSuccessListener { requestDoc ->
                if (!requestDoc.exists()) {
                    Toast.makeText(context, "Request not found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val employeeName = requestDoc.getString("employeeName") ?: "Employee"

                requestRef.update("status", newStatus)
                    .addOnSuccessListener {
                        Log.d("HolidayRequest", "Status updated")

                        db.collection("adminNotifications").document(requestId).delete()

                        // 🔁 Fetch employee document by email instead of ID
                        db.collection("holidayRequests")
                            .whereEqualTo("employeeEmail", employeeEmail)
                            .limit(1)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                if (querySnapshot.isEmpty) {
                                    Toast.makeText(context, "Employee not found", Toast.LENGTH_SHORT).show()
                                    return@addOnSuccessListener
                                }

                                val empDoc = querySnapshot.documents[0]
                                val token = empDoc.getString("fcmToken")
                                val name = empDoc.getString("name") ?: employeeName

                                if (!token.isNullOrEmpty()) {
                                    val title = "Holiday $newStatus"
                                    val body = "$name: $message"

                                    sendPushNotification(token, title, body, context)

                                    val notification = mapOf(
                                        "employeeEmail" to employeeEmail, // 🔁 use email instead of ID
                                        "title" to title,
                                        "message" to body,
                                        "timestamp" to Timestamp.now()
                                    )

                                    db.collection("employeeNotifications")
                                        .add(notification)
                                        .addOnSuccessListener {
                                            Log.d("Firestore", "Notification added")
                                        }
                                        .addOnFailureListener {
                                            Log.e("Firestore", "Failed to save notif: ${it.message}")
                                        }
                                } else {
                                    Toast.makeText(context, "FCM Token missing", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener {
                                Log.e("FetchEmp", "Error: ${it.message}")
                            }
                    }
                    .addOnFailureListener {
                        Log.e("HolidayRequest", "Failed to update: ${it.message}")
                    }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to fetch request", Toast.LENGTH_SHORT).show()
            }
    }



    private fun sendPushNotification(token: String, title: String, body: String, context: Context) {
        val json = JSONObject().apply {
            put("to", token)
            put("notification", JSONObject().apply {
                put("title", title)
                put("body", body)
            })
        }

        val request = object : JsonObjectRequest(
            Method.POST,
            "https://fcm.googleapis.com/fcm/send",
            json,
            { response -> Log.d("FCM", "Success: $response") },
            { error -> Log.e("FCM", "Error: ${error.message}") }
        ) {
            override fun getHeaders(): Map<String, String> = mapOf(
                "Content-Type" to "application/json",
                "Authorization" to "d815715da31ec96ee7bfe6cfe4eabaddc93f50bc" // Replace this
            )
        }

        Volley.newRequestQueue(context).add(request)
    }
}
