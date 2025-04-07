package com.example.nfc_gatway.DataModels

data class LoginRequest(
    val email: String,
    val password: String
)

data class EmployeeRequest(
    val name: String,
    val email: String,
    val department: String,
    val assignedProjects: List<String>,
    val isAdmin: Boolean
)