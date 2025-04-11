package com.example.nfc_gatway.screen.createEmployee

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nfc_gatway.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfc_gatway.datastore.TokenManager
import com.example.nfc_gatway.viewmodels.CreateEmployeeViewModel.CreateEmployeeViewModel

@Composable
fun CreateEmployeeScreen(
    navController: NavController,
    viewModel: CreateEmployeeViewModel,
    tokenManager: TokenManager
) {
    val state = viewModel.createEmployeeState.value
    val context = LocalContext.current
    // Input fields
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val department = remember { mutableStateOf("") }
    val project = remember { mutableStateOf("") }
    val isAdmin = remember { mutableStateOf(false) }
    val agreed = remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    // Show dialog when employee is created successfully
    LaunchedEffect(state.data) {
        if (state.data == true) {
            showSuccessDialog = true
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
            },
            title = {
                Text(text = "Success")
            },
            text = {
                Text("Employee created successfully.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        // Optional: Clear input fields
                        name.value = ""
                        email.value = ""
                        department.value = ""
                        project.value = ""
                        isAdmin.value = false
                        agreed.value = false
                        navController.popBackStack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF014BD4))
    ) {
val token = tokenManager.getToken(context)
        Column {
            // Top Section with Curve
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(Color(0xFF014BD4))
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White, modifier = Modifier.padding(top = 35.dp , start = 10.dp).clickable{
                        navController.popBackStack()
                    }
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
//                            .padding(end = 40.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
//                                .background(Color.Gray)
                                .padding(start = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.nfctools),
                                contentDescription = "NFC Logo",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Column(
                            modifier = Modifier.padding(start = 20.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Create", color = Color.White, fontWeight = FontWeight.SemiBold,fontSize = 25.sp)
                            Text("Employee", color = Color.White,fontWeight = FontWeight.SemiBold, fontSize = 25.sp)
                            Text("Account", color = Color.White, fontWeight = FontWeight.SemiBold,fontSize = 25.sp)
                        }
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(
                    topStart = 35.dp,
                    topEnd = 35.dp
                ), // Curved top corners
                color = Color.White,
                modifier = Modifier
                    .fillMaxHeight()

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Drag Handle (Gray Bar)
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(5.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(2.dp))
                    )

                    Spacer(modifier = Modifier.height(17.dp))
                    Text("Entre the Details", color = Color.Black, fontSize = 25.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Email Input
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Employee Name", color = Color.DarkGray) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email", color = Color.DarkGray) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = department.value ,
                        onValueChange = { department.value = it },
                        label = { Text("department", color = Color.DarkGray) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value =project.value,
                        onValueChange = { project.value = it  },
                        label = { Text("Assigned Project(s)", color = Color.DarkGray) },
                        singleLine = true,
                        placeholder = { Text("Separate with commas") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )

                    Spacer(modifier = Modifier.height(17.dp))

                    Text("Is Admin?", fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        RadioButton(
                            selected = isAdmin.value,
                            onClick = { isAdmin.value = true }
                        )
                        Text("Yes")

                        Spacer(modifier = Modifier.width(24.dp))

                        RadioButton(
                            selected = !isAdmin.value,
                            onClick = { isAdmin.value = false }
                        )
                        Text("No")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Checkbox(
                            checked = agreed.value,
                            onCheckedChange = { agreed.value = it }
                        )
                        Row {
                            Text("Agree with ")
                            Text("TERMS & CONDITIONS",color = Color(0xFF014BD4))
                        }
                    }

                    Button(
                        onClick = {
                            if (agreed.value) {
                                viewModel.createEmployee(
                                    name.value,
                                    email.value,
                                    department.value,
                                    project.value,
                                    isAdmin.value,
                                    navController
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF014BD4)
            )
                    ) {
                        Text(if (state.loading) "Creating..." else "CREATE ACCOUNT")
                    }
                }
            }
        }
    }
}