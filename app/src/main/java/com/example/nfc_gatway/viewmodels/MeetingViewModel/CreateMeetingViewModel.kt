package com.example.nfc_gatway.viewmodels.MeetingViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.android.volley.Response
import org.json.JSONObject
import java.util.UUID

class CreateMeetingViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _meetingState = MutableStateFlow<String?>(null)
    val meetingState: StateFlow<String?> = _meetingState.asStateFlow()

    fun createMeeting(
        meetingName: String,
        description: String,
        cabinNo: String,
        startTime: String,
        endTime: String,
        context: Context
    ) {
        val meetingId = UUID.randomUUID().toString()
        val meetingData = hashMapOf(
            "id" to meetingId,
            "meetingName" to meetingName,
            "description" to description,
            "cabinNo" to cabinNo,
            "startTime" to startTime,
            "endTime" to endTime,
            "createdAt" to Timestamp.now()
        )

        db.collection("meetings")
            .document(meetingId)
            .set(meetingData)
            .addOnSuccessListener {
                _meetingState.value = "Meeting created successfully"
                sendNotificationToEmployees(context, meetingName, description)
            }
            .addOnFailureListener {
                _meetingState.value = "Error creating meeting: ${it.message}"
                Log.e("Firestore", "Error: ${it.message}", it)
            }
    }

    private fun sendNotificationToEmployees(context: Context, title: String, body: String) {
        db.collection("employees")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val token = document.getString("fcmToken")
                    if (!token.isNullOrEmpty()) {
                        sendPushNotification(context, token, title, body)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("Firestore", "Error fetching employees: ${it.message}", it)
            }
    }

    private fun sendPushNotification(context: Context, token: String, title: String, message: String) {
        val json = JSONObject().apply {
            put("to", token)
            put("notification", JSONObject().apply {
                put("title", title)
                put("body", message)
            })
        }

        val request = object : StringRequest(
            Method.POST,
            "https://fcm.googleapis.com/fcm/send",
            Response.Listener<String> { response ->
                Log.d("FCM", "Notification sent: $response")
            },
            Response.ErrorListener { error ->
                Log.e("FCM", "Push failed", error)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "key=YOUR_SERVER_KEY" // 🔐 Replace with real key
                headers["Content-Type"] = "application/json"
                return headers
            }

            override fun getBody(): ByteArray {
                return json.toString().toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(request)
    }
}
