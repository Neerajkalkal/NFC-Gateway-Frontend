package com.example.nfc_gatway.Network.ApiService

import com.example.nfc_gatway.DataModels.EmployeeRequest
import com.example.nfc_gatway.DataModels.LoginRequest
import com.example.nfc_gatway.DataModels.Employee
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/api/employee/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<String>

    @GET("/api/employee/{email}")
    suspend fun getEmployee(
        @Path("email") email: String,
        @Header("Authorization") token: String
    ): Employee

    @POST("/api/admin/create_employee")
    suspend fun createEmployee(
        @Header("Authorization") token: String,
        @Body employee: EmployeeRequest
    ): Response<Void>
}
