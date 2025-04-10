package com.example.nfc_gatway.screen.MeetingScreen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfc_gatway.R
import com.example.nfc_gatway.viewmodels.MeetingViewModel.CreateMeetingViewModel

@Composable
fun MeetingScreen(viewModel: CreateMeetingViewModel = viewModel() , navController: NavController){
    var meetingName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cabinNo by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    val meetingState by viewModel.meetingState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

//     Show dialog when employee is created successfully
    LaunchedEffect(meetingState) {
        if (meetingState == "Meeting created successfully") {
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
                Text("Meeting created successfully.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                    meetingName = ""
                    description = ""
                    cabinNo = ""
                    startTime = ""
                    endTime = ""

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
                    tint = Color.White, modifier = Modifier.padding(top = 35.dp , start = 10.dp).clickable{
//                        navController.navigate("admin/${email}/${token}") {
//                            popUpTo("creteemployee") { inclusive = true }
//                        }
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
                            Text("Meeting", color = Color.White,fontWeight = FontWeight.SemiBold, fontSize = 25.sp)
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
                    Text("Of Meeting", color = Color.Black, fontSize = 17.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Email Input
                    OutlinedTextField(
                        value = meetingName,
                        onValueChange = { meetingName = it },
                        label = { Text("Meeting Name", color = Color.DarkGray) },
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
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Discription", color = Color.DarkGray) },
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
                        value =cabinNo ,
                        onValueChange = { cabinNo = it },
                        label = { Text("Cabin No.", color = Color.DarkGray) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Cabin,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text("Meeting Start Time", color = Color.DarkGray) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Timer,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("Meeting End Time", color = Color.DarkGray) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Timer,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10)
                    )

                    Spacer(modifier = Modifier.height(17.dp))

                    Button(
                        onClick = {
                            viewModel.createMeeting(
                                meetingName,
                                description,
                                cabinNo,
                                startTime,
                                endTime,
                                context
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF014BD4)
                        )
                    ) {
                        Text(if (meetingState == "Creating") "Creating..." else "CREATE MEETING")
//                        Text("CREATE MEETING")
                    }
                }
            }
        }
    }
}