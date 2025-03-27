package com.example.nfc_gatway.Network.ApiService

import com.example.nfc_gatway.DataModels.LoginRequest
import com.example.nfc_gatway.DataModels.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/api/employee/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body req: LoginRequest): Response<ResponseBody>
}
