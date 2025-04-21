package com.example.nfc_gatway.viewmodels.AttendanceViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.nfc_gatway.datastore.TokenManager
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AttendanceViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val attendanceCollection = db.collection("Attendance")
    private val _navigationEvent = MutableLiveData<Boolean>() // true = success, false = failure
    val navigationEvent: LiveData<Boolean> = _navigationEvent

    private fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    }

    fun markAttendance(token: String, nfcId: String) {
        viewModelScope.launch {
            try {
                val jwt = JWT(token)
                val email = jwt.getClaim("email").asString()
                val name = jwt.getClaim("name").asString()

                if (email.isNullOrEmpty() || name.isNullOrEmpty()) {
                    Log.e("Attendance", "Invalid token")
                    _navigationEvent.postValue(false)
                    return@launch
                }

                val docId = "${email}_$nfcId"
                val today = getTodayDate()
                val currentTime = getCurrentTime()

                attendanceCollection.document(docId).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            handleExistingDocument(document, today, currentTime, docId)
                        } else {
                            createNewDocument(email, nfcId, name, today, currentTime)
                        }
                    }
                    .addOnFailureListener {
                        Log.e("Attendance", "Document check failed", it)
                        _navigationEvent.postValue(false)
                    }

            } catch (e: Exception) {
                Log.e("Attendance", "Token error", e)
                _navigationEvent.postValue(false)
            }
        }
    }

    private fun handleExistingDocument(
        document: DocumentSnapshot,
        today: String,
        currentTime: String,
        docId: String
    ) {
        val attendanceLogs = document.get("attendanceLogs") as? Map<*, *> ?: emptyMap<Any, Any>()
        val todayLog = attendanceLogs[today] as? Map<*, *>

        when {
            todayLog == null -> {
                // First attendance of the day — mark entry
                markEntry(docId, today, currentTime)
            }
            todayLog["exit"] == null -> {
                // Second scan — mark exit
                markExit(docId, today, currentTime)
            }
            else -> {
                // Already marked entry and exit
                Log.d("Attendance", "Already marked twice")
                _navigationEvent.postValue(true)
            }
        }
    }


    private fun markEntry(docId: String, today: String, currentTime: String) {
        attendanceCollection.document(docId)
            .update("attendanceLogs.$today.entry", currentTime)
            .addOnSuccessListener {
                Log.d("Attendance", "Entry marked")
                _navigationEvent.postValue(true)
            }
            .addOnFailureListener { e ->
                Log.e("Attendance", "Entry failed", e)
                _navigationEvent.postValue(false)
            }
    }

    private fun markExit(docId: String, today: String, currentTime: String) {
        attendanceCollection.document(docId)
            .update("attendanceLogs.$today.exit", currentTime)
            .addOnSuccessListener {
                Log.d("Attendance", "Exit marked")
                _navigationEvent.postValue(true)
            }
            .addOnFailureListener { e ->
                Log.e("Attendance", "Exit failed", e)
                _navigationEvent.postValue(false)
            }
    }

    private fun createNewDocument(email: String, nfcId: String, name: String, today: String, currentTime: String) {
        val newData = hashMapOf(
            "email" to email,
            "nfcId" to nfcId,
            "name" to name,
            "attendanceLogs" to hashMapOf(
                today to hashMapOf(
                    "entry" to currentTime
                )
            )
        )

        attendanceCollection.document("${email}_$nfcId")
            .set(newData)
            .addOnSuccessListener {
                Log.d("Attendance", "New entry created")
                _navigationEvent.postValue(true)
            }
            .addOnFailureListener { e ->
                Log.e("Attendance", "Create failed", e)
                _navigationEvent.postValue(false)
            }
    }
}
