package com.example.nfc_gatway.viewmodels.CreateEmployeeViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.nfc_gatway.DataModels.EmployeeRequest
import com.example.nfc_gatway.Repository.EmployeeRepository
import com.example.nfc_gatway.datastore.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Response


data class DataOrException<T, E : Exception>(
    var data: T? = null,
    var loading: Boolean = false,
    var e: E? = null
)


class CreateEmployeeViewModel(
    private val context: Context,
    private val repository: EmployeeRepository
) : ViewModel() {

    var createEmployeeState = mutableStateOf(DataOrException<Boolean, Exception>(loading = false))

    fun createEmployee(
        name: String,
        email: String,
        department: String,
        assignedProjects : String,
        isAdmin: Boolean,
        navController: NavController
    ) {
        viewModelScope.launch {
            createEmployeeState.value = DataOrException(loading = true)

            try {
                val token = TokenManager.getToken(context)

                if (token.isNullOrBlank()) {
                    createEmployeeState.value = DataOrException(e = Exception("Token not found"))
                    return@launch
                }

                val request = EmployeeRequest(
                    name = name,
                    email = email,
                    department = department,
                    assignedProjects = assignedProjects.split(",").map { it.trim() },
                    isAdmin = isAdmin
                )

                val response = repository.createEmployee("Bearer $token", request)

                if (response.isSuccessful) {
                    createEmployeeState.value = DataOrException(data = true)
                    navController.navigate("admin/$email/$token") {
                        popUpTo("create_employee") { inclusive = true }
                    }
                } else {
                    createEmployeeState.value = DataOrException(e = Exception("Failed with code: ${response.code()}"))
                }

            } catch (e: Exception) {
                createEmployeeState.value = DataOrException(e = e)
            }
        }
    }
}