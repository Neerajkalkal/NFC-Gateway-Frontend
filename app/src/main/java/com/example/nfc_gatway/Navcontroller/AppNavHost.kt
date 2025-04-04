package com.example.nfc_gatway.Navcontroller

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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
            EmployeeLoginScreen(
                navController = navController
            )
        }
        composable("home/{email}/{token}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HomeScreen(email = email, token = token)
        }
    }
}