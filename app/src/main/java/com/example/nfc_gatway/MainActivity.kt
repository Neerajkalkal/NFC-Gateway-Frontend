package com.example.nfc_gatway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nfc_gatway.Navcontroller.AppNavHost

import com.example.nfc_gatway.screen.LoginScreen.EmployeeLoginScreen
import com.example.nfc_gatway.ui.theme.NfcGatwayTheme
import com.example.nfc_gatway.viewmodels.LoginScreenviewmodel.EmployeeLoginViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NfcGatwayTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: EmployeeLoginViewModel = viewModel()
    AppNavHost(navController, viewModel)
}
