package com.example.nfc_gatway.viewmodels.LoginScreenviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.nfc_gatway.DataModels.LoginRequest
import com.example.nfc_gatway.DataModels.loginResponse
import com.example.nfc_gatway.Network.Retrofit.RetrofitInstance
import com.example.nfc_gatway.datastore.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject



data class DataOrException<T, E : Exception>(
    var data: T? = null,
    var loading: Boolean = false,
    var e: E? = null
)


class EmployeeLoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    // Input states
    var email = MutableStateFlow("")
        private set

    var password = MutableStateFlow("")
        private set

    // Login response state
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

//                    val token = response.token  // assuming your loginResponse has a field `token`
//                    TokenManager.saveToken(context, token)
//
//                    _loginResponse.value = DataOrException(data = token, loading = false)
                if (response.isSuccessful) {
                    val rawBody = response.body()

                    if (!rawBody.isNullOrEmpty()) {
                        // Save the token assuming the response is a plain string
                        TokenManager.saveToken(context, rawBody)

                        // Pass token to the UI
                        _loginResponse.value = DataOrException(data = rawBody, loading = false)
                    } else {
                        _loginResponse.value = DataOrException(
                            e = Exception("Invalid response"),
                            loading = false
                        )
                    }
                } else {
                    val errorMsg = "Login failed: ${response.code()} ${response.message()}"
                    _loginResponse.value = DataOrException(
                        e = Exception(errorMsg),
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _loginResponse.value = DataOrException(e = e, loading = false)
            }
        }
    }
}