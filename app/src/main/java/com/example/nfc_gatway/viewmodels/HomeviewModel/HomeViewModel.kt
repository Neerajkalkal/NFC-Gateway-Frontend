package com.example.nfc_gatway.viewmodels.HomeviewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfc_gatway.Network.Retrofit.RetrofitInstance
import com.example.nfc_gatway.DataModels.Employee
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val employee = mutableStateOf<Employee?>(null)

    fun fetchEmployee(email: String, token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEmployee(email, "Bearer $token")
                employee.value = response
            } catch (e: Exception) {
                Log.e("FetchEmployee", "Error: ${e.message}")
            }
        }
    }
}