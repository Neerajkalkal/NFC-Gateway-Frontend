package com.example.nfc_gatway.Repository

import com.example.nfc_gatway.DataModels.EmployeeRequest
import com.example.nfc_gatway.Network.ApiService.ApiService
import retrofit2.Response


class EmployeeRepository(private val api: ApiService) {

    suspend fun createEmployee(token: String, employee: EmployeeRequest): Response<Void> {
        return api.createEmployee(token, employee)
    }
}