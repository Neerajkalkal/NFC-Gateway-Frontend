package com.example.nfc_gatway.viewmodels.ProfileViewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class ProfileViewModel : ViewModel() {
    fun uploadImageToFirebase(imageUri: Uri, context: Context) {
        val storageRef = Firebase.storage.reference.child("profile_images/${UUID.randomUUID()}.jpg")

        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("Firebase", "Image URL: $uri")
                    Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.e("Firebase", "Upload failed", it)
                Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
            }
    }
}