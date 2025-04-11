package com.example.nfc_gatway.screen.HolidayRequestScreen

import android.R.attr.text
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfc_gatway.R
import com.example.nfc_gatway.datastore.TokenManager
import com.example.nfc_gatway.datastore.decodeJwtToken
import com.example.nfc_gatway.viewmodels.HolidayRequestViewModel.HolidayRequestViewModel
import java.text.SimpleDateFormat
import com.google.firebase.Timestamp
import java.util.Locale

@Composable
fun HolidayRequestScreen(
    navController: NavController,
    viewModel: HolidayRequestViewModel = viewModel()
) {
    val context = LocalContext.current

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var employeeName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val requestState by viewModel.requestState.collectAsState()

    // Load employee name once from token
    LaunchedEffect(Unit) {
        val token = TokenManager.getToken(context)
        val userInfo = token?.let { decodeJwtToken(it) }
        employeeName = userInfo?.get("name")?.toString().orEmpty()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF014BD4))
    ) {
//        val token = tokenManager.getToken(context)
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
                    tint = Color.White,
                    modifier = Modifier.padding(top = 35.dp, start = 10.dp).clickable {
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
                            Text(
                                "Apply",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 25.sp
                            )
                            Text(
                                "Holiyday",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 25.sp
                            )
                            Text(
                                "Account",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 25.sp
                            )
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Holiday Request",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = { Text("Start Date (yyyy-MM-dd)", color = Color.DarkGray) },
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(10)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = { Text("End Date (yyyy-MM-dd)", color = Color.DarkGray) },
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(10)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = reason,
                            onValueChange = { reason = it },
                            label = { Text("Reason", color = Color.DarkGray) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Message,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(10)
                        )
                        Spacer(modifier = Modifier.height(17.dp))

                        Button(
                            onClick = {
                                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                try {
                                    val startTimestamp = Timestamp(sdf.parse(startDate)!!)
                                    val endTimestamp = Timestamp(sdf.parse(endDate)!!)

                                    if (employeeName.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Employee name not found",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@Button
                                    }

                                    viewModel.submitHolidayRequest(
                                        employeeName = employeeName,
                                        startDate = startTimestamp,
                                        endDate = endTimestamp,
                                        reason = reason,
                                        context = context
                                    )
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Invalid date format",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                showDialog = true
                            },
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF014BD4)
                            )
                        ) {
                            Text("Submit Request")
                        }
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Success") },
                                text = {
                                    Text(
                                        "Your holiday request was submitted successfully!",
                                        fontSize = 19.sp
                                    )
                                },
                                confirmButton = {
                                    TextButton(onClick = {
                                        reason = ""
                                        startDate = ""
                                        endDate = ""

                                        showDialog = false

                                        navController.popBackStack()
                                    }) {
                                        Text("OK")
                                    }
                                }
                            )

                        }

                    }
                }
            }
        }
    }
}
