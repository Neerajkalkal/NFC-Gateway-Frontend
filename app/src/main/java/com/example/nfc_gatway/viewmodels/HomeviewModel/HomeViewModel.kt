package com.example.nfc_gatway.viewmodels.HomeviewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.nfc_gatway.Network.Retrofit.RetrofitInstance
import com.example.nfc_gatway.DataModels.Employee
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val employee = mutableStateOf<Employee?>(null)

    fun fetchEmployee(email: String, token: String) {
        viewModelScope.launch {
            try {
                Log.d("FetchEmployee", "Fetching employee for $email")
                val response = RetrofitInstance.api.getEmployee(email, "Bearer $token")
                employee.value = response
            } catch (e: Exception) {
                Log.e("FetchEmployee", "Error: ${e.message}")
            }
        }
    }
        fun logoutUser(context: Context, navController: NavHostController) {
            val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            navController.navigate("Main") {
                popUpTo(0)
                launchSingleTop = true
            }
        }
}