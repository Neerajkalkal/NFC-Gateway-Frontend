package com.example.nfc_gatway.screen.MainScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nfc_gatway.R
import com.example.nfc_gatway.viewmodels.HomeviewModel.HomeViewModel
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), email: String, token: String) {
    val employee by viewModel.employee
val context = LocalContext.current
    // Fetch employee data when screen loads
    LaunchedEffect(email) {
        viewModel.fetchEmployee(email,token)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF014BD4))
    ) {
        Column {
            // Top Section with Curve
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(Color(0xFF014BD4))
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 40.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .padding(start = 10.dp)
                        ) {
//                            imageUri.value?.let {
//                                Image(
//                                    painter = rememberAsyncImagePainter(it),
//                                    contentDescription = "Profile Image",
//                                    modifier = Modifier.fillMaxSize(),
//                                    contentScale = ContentScale.Crop
//                                )
//                            } ?: Icon(
//                                imageVector = Icons.Default.CameraAlt,
//                                contentDescription = "Upload Image",
//                                tint = Color.White,
//                                modifier = Modifier.size(30.dp)
//                            )
//                        }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                            Column(
                                modifier = Modifier.padding(start = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                employee?.let { emp ->
                                Text(
                                    "Hi,${emp.name}",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("${emp.department}", color = Color.White, fontSize = 16.sp)
                                Text("${emp.employeeId},${emp.email}", color = Color.White, fontSize = 14.sp)
                            } ?:run{
                                        CircularProgressIndicator(color = Color.Black)
                                    }
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
                        .padding(12.dp), // Inner padding
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

                    // Info Text
                    Text(
                        text = "Effortlessly manage attendance and workplace activities with NFC technology. " +
                                "Employees can check in/out with a simple tap, access real-time schedules, " +
                                "collaborate on projects, and stay connected—all in one seamless system.",
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp) // Left & Right spacing
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val context = LocalContext.current
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable {
                                        Toast.makeText(
                                            context,
                                            "Attendance Clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(107.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color(0xFF014BD4)), // Purple
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.nfctools),
                                            contentDescription = "Attendance",
                                            modifier = Modifier.size(60.dp),
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Attendance", fontSize = 14.sp, color = Color.Black)
                                }
                            }

                            item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable {
                                        Toast.makeText(
                                            context,
                                            "Meeting Clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(107.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color(0xFF014BD4)), // Purple
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.meeting),
                                            contentDescription = "Meeting",
                                            modifier = Modifier.size(60.dp),
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Meeting", fontSize = 14.sp, color = Color.Black)
                                }
                            }

                            item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable {
                                        Toast.makeText(
                                            context,
                                            "Holiday-Apply Clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(107.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color(0xFF014BD4)), // Purple
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.holiday),
                                            contentDescription = "Holiday Apply",
                                            modifier = Modifier.size(60.dp),
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Holiday-Apply",
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }

                            item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable {
                                        Toast.makeText(
                                            context,
                                            "Vending Machine Clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(107.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color(0xFF014BD4)), // Purple
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.vending_machine),
                                            contentDescription = "Vending Machine",
                                            modifier = Modifier.size(60.dp),
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Vending Machine",
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(17.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            // Logout Button
                            Button(
                                onClick = { /* Handle logout */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF014BD4
                                    )
                                ),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(45.dp)
                            ) {
                                Text(text = "Logout", color = Color.White, fontSize = 16.sp)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Divider Line
                            Divider(color = Color.Gray, thickness = 2.dp)

                            Spacer(modifier = Modifier.height(8.dp))

                            // Bottom Navigation Bar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.home),
                                    contentDescription = "Home",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(33.dp)
                                        .clickable {}
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.chat),
                                    contentDescription = "Chat",
                                    tint = Color(0xFF014BD4),
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clickable {}
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.notification),
                                    contentDescription = "Notifications",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(33.dp)
                                        .clickable {}
                                )
                            }
                        }
                    }
                }

                }
            }
        }

}



