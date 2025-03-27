package com.example.nfc_gatway.viewmodels.LoginScreenviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nfc_gatway.DataModels.LoginRequest
import com.example.nfc_gatway.Network.Retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject


data class DataOrException<T, E : Exception>(
    var data: T? = null,
    var loading: Boolean = false,
    var e: E? = null
)

class EmployeeLoginViewModel : ViewModel() {

    // Input fields
    var email = MutableStateFlow("")
        private set

    var password = MutableStateFlow("")
        private set

    // Unified login response state
    private val _loginResponse = MutableStateFlow(DataOrException<String, Exception>())
    val loginResponse = _loginResponse.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            _loginResponse.value = DataOrException(loading = true)
            try {
                val response = RetrofitInstance.api.login(LoginRequest(email.value, password.value))
                if (response.isSuccessful) {
                    val rawBody = response.body()?.string()
                    rawBody?.let { body ->
                        if (body.startsWith("{")) {
                            // Handle JSON response
                            val jsonObject = JSONObject(body)
                            val token = jsonObject.optString("token", "")
                            val message = jsonObject.optString("message", "")

                            // You can choose what to expose (token, message, both)
                            _loginResponse.value = DataOrException(data = token.ifEmpty { message }, loading = false)

                        } else {
                            // Handle plain string response
                            _loginResponse.value = DataOrException(data = body, loading = false)
                        }
                    } ?: run {
                        _loginResponse.value = DataOrException(
                            e = Exception("Empty response body"), loading = false
                        )
                    }
                } else {
                    _loginResponse.value = DataOrException(
                        e = Exception("Login failed: ${response.message()}"),
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _loginResponse.value = DataOrException(e = e, loading = false)
            }
        }
    }
}