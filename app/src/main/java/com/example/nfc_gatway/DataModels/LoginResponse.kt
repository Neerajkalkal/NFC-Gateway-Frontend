package com.example.nfc_gatway.DataModels

data class loginResponse(
    val token: String,
    val email: String
)

data class EmployeeResponse(
    val success: Boolean,
    val message: String
)