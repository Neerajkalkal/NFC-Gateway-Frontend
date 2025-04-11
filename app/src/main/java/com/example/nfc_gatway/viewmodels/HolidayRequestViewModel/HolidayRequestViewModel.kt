package com.example.nfc_gatway.viewmodels.HolidayRequestViewModel

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nfc_gatway.datastore.TokenManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID


class HolidayRequestViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _requestState = MutableStateFlow<String?>(null)
    val requestState: StateFlow<String?> = _requestState

    fun submitHolidayRequest(
        employeeName: String,
        startDate: Timestamp,
        endDate: Timestamp,
        reason: String,
        context: Context
    ) {
        val tokenManager = TokenManager
        val employeeEmail = tokenManager.getEmailFromToken(context)

        Log.d("HolidayRequest", "Decoded email from token: $employeeEmail")

        if (employeeEmail.isBlank()) {
            _requestState.value = "Failed: Email not found in token. Please log in again."
            return
        }

        val requestId = UUID.randomUUID().toString()
        val request = mapOf(
            "employeeEmail" to employeeEmail,
            "employeeName" to employeeName,
            "startDate" to startDate,
            "endDate" to endDate,
            "reason" to reason,
            "status" to "pending",
            "createdAt" to Timestamp.now()
        )

        db.collection("holidayRequests").document(requestId).set(request)
            .addOnSuccessListener {
                _requestState.value = "Request submitted"
                val title = "New Holiday Request"
                val message = "$employeeName has applied for leave."

                sendNotificationToAdmin(context, title, message)
                saveNotificationToFirestore(
                    requestId = requestId,
                    employeeEmail = employeeEmail,
                    employeeName = employeeName,
                    startDate = startDate,
                    endDate = endDate,
                    reason = reason
                )
            }
            .addOnFailureListener {
                _requestState.value = "Failed: ${it.message}"
            }
    }

    private fun sendNotificationToAdmin(context: Context, title: String, message: String) {
        db.collection("admins").get().addOnSuccessListener { snapshot ->
            snapshot.forEach { admin ->
                admin.getString("fcmToken")?.let { token ->
                    sendPushNotification(context, token, title, message)
                }
            }
        }
    }
//    fun getHolidayNotification(employeeId: String, onNotificationFetched: (DocumentSnapshot) -> Unit) {
//        firestore.collection("notifications")
//            .document(employeeId)
//            .collection("holiday")
//            .orderBy("timestamp", Query.Direction.DESCENDING)
//            .limit(1)
//            .get()
//            .addOnSuccessListener { snapshot ->
//                val doc = snapshot.documents.firstOrNull()
//                doc?.let { onNotificationFetched(it) }
//            }
//    }
    private fun saveNotificationToFirestore(
        requestId: String,
        employeeEmail: String,
        employeeName: String,
        startDate: Timestamp,
        endDate: Timestamp,
        reason: String,
        status: String = "pending"
    ) {
        val notification = mapOf(
            "requestId" to requestId,
            "employeeEmail" to employeeEmail,
            "employeeName" to employeeName,
            "startDate" to startDate,
            "endDate" to endDate,
            "reason" to reason,
            "status" to status,
            "timestamp" to Timestamp.now()
        )

        db.collection("adminNotifications").document(requestId).set(notification)
            .addOnSuccessListener {
                Log.d("Notification", "Notification saved to Firestore")
            }
            .addOnFailureListener {
                Log.e("Notification", "Failed to save notification: ${it.message}")
            }
    }

    private fun sendPushNotification(context: Context, token: String, title: String, body: String) {
        val json = JSONObject().apply {
            put("to", token)
            put("notification", JSONObject().apply {
                put("title", title)
                put("body", body)
            })
        }

        val request = object : StringRequest(
            Method.POST,
            "https://fcm.googleapis.com/fcm/send",
            Response.Listener<String> { Log.d("FCM", "Notification sent: $it") },
            Response.ErrorListener { Log.e("FCM", "Notification failed", it) }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "Authorization" to "key=YOUR_SERVER_KEY", // replace this
                    "Content-Type" to "application/json"
                )
            }

            override fun getBody(): ByteArray = json.toString().toByteArray()
        }

        Volley.newRequestQueue(context).add(request)
    }
}
