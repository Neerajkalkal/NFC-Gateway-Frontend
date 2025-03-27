package com.example.nfc_gatway.Navcontroller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nfc_gatway.screen.LoginScreen.EmployeeLoginScreen
import com.example.nfc_gatway.screen.MainScreen.HomeScreen
import com.example.nfc_gatway.viewmodels.LoginScreenviewmodel.EmployeeLoginViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: EmployeeLoginViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            EmployeeLoginScreen(viewModel, navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}