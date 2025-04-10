package com.example.nfc_gatway.Navcontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nfc_gatway.Network.Retrofit.RetrofitInstance
import com.example.nfc_gatway.Repository.EmployeeRepository
import com.example.nfc_gatway.datastore.TokenManager
import com.example.nfc_gatway.screen.AdminScreen.AdminScreen
import com.example.nfc_gatway.screen.ChoiceScreen.MainScreen
import com.example.nfc_gatway.screen.EmoloyeeMeeting.EmployeeMeetingScreen
import com.example.nfc_gatway.screen.LoginScreen.AdminLoginScreen
import com.example.nfc_gatway.screen.LoginScreen.EmployeeLoginScreen
import com.example.nfc_gatway.screen.MainScreen.HomeScreen
import com.example.nfc_gatway.screen.MeetingScreen.MeetingScreen
import com.example.nfc_gatway.screen.NfcScreen.AttendancePopupScreen
import com.example.nfc_gatway.screen.createEmployee.CreateEmployeeScreen
import com.example.nfc_gatway.viewmodels.CreateEmployeeViewModel.CreateEmployeeViewModel
import com.example.nfc_gatway.viewmodels.LoginScreenviewmodel.EmployeeLoginViewModel
import com.example.nfc_gatway.viewmodels.MeetingViewModel.CreateMeetingViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: EmployeeLoginViewModel) {
    NavHost(navController = navController, startDestination = "Main") {
        composable("Main") {
            MainScreen{
                isAdmin ->
                if (isAdmin){
                    navController.navigate("adminlogin")
                }else{
                    navController.navigate("employeelogin")
                }
            }
        }
        composable("adminlogin") {
            AdminLoginScreen(
                navController = navController
            )
        }
        composable("employeelogin") {
            EmployeeLoginScreen(
                navController = navController
            )
        }
        composable("createmeeting") {
            MeetingScreen(
            navController = navController
            )
        }
        composable("employee_meeting") {
            EmployeeMeetingScreen(
                navController = navController
            )
        }
        composable("creteemployee") {
            val context = LocalContext.current
            val viewModel = remember {
                CreateEmployeeViewModel(
                    context = context,
                    repository = EmployeeRepository(RetrofitInstance.api)
                )
            }
            CreateEmployeeScreen(
                navController = navController,
                viewModel = viewModel,
                tokenManager = TokenManager
            )

        }
        composable(
            "nfc_popup/{token}/{nfcId}",
            arguments = listOf(
                navArgument("token") { type = NavType.StringType },
                navArgument("nfcId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val nfcId = backStackEntry.arguments?.getString("nfcId") ?: ""

            AttendancePopupScreen(
                isVisible = true,
                token = token,
                nfcId = nfcId,
                onDismiss = {
                    navController.popBackStack()
                },
                navController = navController
            )
        }
        composable("admin/{email}/{token}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AdminScreen(email = email, token = token , navController = navController)
        }
        composable("home/{email}/{token}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HomeScreen(email = email, token = token, navController = navController)
        }
    }
}

