package com.example.nfc_gatway.viewmodels.AttendanceViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AttendanceViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val attendanceCollection = db.collection("Attendance")

    private fun getTodayDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(Date())
    }

    fun markAttendance(token: String, nfcId: String, function: () -> Unit) {
        viewModelScope.launch {
            try {
                val jwt = JWT(token)
                val employeeId = jwt.getClaim("employeeId").asString()
                val name = jwt.getClaim("name").asString()

                if (employeeId.isNullOrEmpty() || name.isNullOrEmpty()) {
                    Log.e("Attendance", "Invalid token. Missing employeeId or name.")
                    return@launch
                }

                val docId = "${employeeId}_$nfcId"
                val today = getTodayDate()
                val currentTime = getCurrentTime()

                Log.d("Attendance", "Checking document: $docId")

                attendanceCollection.document(docId).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            Log.d("Attendance", "Document exists")

                            val attendanceLogs = document.get("attendanceLogs") as? Map<*, *>
                            val todayLog = attendanceLogs?.get(today) as? Map<*, *>
                            val entry = todayLog?.get("entry") as? String
                            val exit = todayLog?.get("exit") as? String

                            when {
                                todayLog == null -> {
                                    val updateData = mapOf("attendanceLogs.$today.entry" to currentTime)
                                    attendanceCollection.document(docId)
                                        .set(updateData, SetOptions.merge())
                                        .addOnSuccessListener {
                                            Log.d("Attendance", "Entry marked at $currentTime")
                                            function()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e("Attendance", "Failed to mark entry", e)
                                        }
                                }

                                entry != null && exit == null -> {
                                    val updateData = mapOf("attendanceLogs.$today.exit" to currentTime)
                                    attendanceCollection.document(docId)
                                        .set(updateData, SetOptions.merge())
                                        .addOnSuccessListener {
                                            Log.d("Attendance", "Exit marked at $currentTime")
                                            function()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e("Attendance", "Failed to mark exit", e)
                                        }
                                }

                                else -> {
                                    Log.d("Attendance", "Attendance already marked twice today.")
                                    function()
                                }
                            }
                        } else {
                            Log.d("Attendance", "Document doesn't exist. Creating new.")

                            val newAttendance = hashMapOf(
                                "employeeId" to employeeId,
                                "nfcId" to nfcId,
                                "name" to name,
                                "attendanceLogs" to hashMapOf(
                                    today to hashMapOf(
                                        "entry" to currentTime
                                    )
                                )
                            )

                            attendanceCollection.document(docId)
                                .set(newAttendance)
                                .addOnSuccessListener {
                                    Log.d("Attendance", "New user registered & Entry marked at $currentTime")
                                    function()
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Attendance", "Failed to register and mark attendance", e)
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Attendance", "Failed to check attendance", e)
                    }

            } catch (e: Exception) {
                Log.e("Attendance", "Failed to decode token or mark attendance", e)
            }
        }
    }
}


