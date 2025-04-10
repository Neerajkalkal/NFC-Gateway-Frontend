package com.example.nfc_gatway.viewmodels.EmployeeMeetingViewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class EmployeeMeetingViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Mutable list that Jetpack Compose can observe
    private val _meetings = mutableStateListOf<Map<String, Any>>()
    val meetings: SnapshotStateList<Map<String, Any>> = _meetings
var isLoading by mutableStateOf(false)
    private set
    init {
        fetchMeetings()
    }

    private fun fetchMeetings() {
        db.collection("meetings")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                _meetings.clear()
                for (document in result) {
                    _meetings.add(document.data)
                }
                isLoading = false
            }
            .addOnFailureListener { e ->
                Log.e("MeetingFetch", "Error fetching meetings: ${e.message}", e)
                isLoading = false
            }
    }
}