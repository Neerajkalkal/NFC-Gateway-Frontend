package com.example.nfc_gatway.screen.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.nfc_gatway.R
import androidx.compose.material3.OutlinedTextField
import com.example.nfc_gatway.viewmodels.LoginScreenviewmodel.EmployeeLoginViewModel


@Composable
fun EmployeeLoginScreen(
    viewModel: EmployeeLoginViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    // Collect your centralized state
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginResponse.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.nfc_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Welcome to NFC-GATEWAY",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email", color = Color.DarkGray) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password", color = Color.DarkGray) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.login()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2D1963)
            ),
            shape = RoundedCornerShape(50)
        ) {
            if (loginState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Show error if present
        loginState.e?.message?.let { errorMsg ->
            Text(
                text = errorMsg,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Auto-Navigate on Success
        loginState.data?.let { token ->
            LaunchedEffect(token) {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Designed by",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = "Diya Narang and Neeraj",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}
