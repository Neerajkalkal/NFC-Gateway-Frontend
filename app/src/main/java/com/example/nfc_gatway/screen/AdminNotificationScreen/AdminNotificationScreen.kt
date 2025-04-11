package com.example.nfc_gatway.screen.AdminNotificationScreen

import android.R
import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfc_gatway.viewmodels.AdminNotificationViewModel.AdminNotificationViewModel

@Composable
fun AdminNotificationScreen(
    navController : NavController,
    viewModel: AdminNotificationViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    val requests by viewModel.notifications.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row (
            modifier = Modifier.fillMaxWidth() .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF014BD4)
                )
            }
            Text(
                text = "Holiday Requests", color = Color(0xFF014BD4),
                fontSize = 24.sp, fontWeight = FontWeight.Bold,
            )
        }

        if (requests.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No holiday requests available.")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(requests) { request ->
                    val employeeName = request["employeeName"] as? String ?: "N/A"
                    val reason = request["reason"] as? String ?: "N/A"
                    val startDate = request["startDate"] as? String ?: "N/A"
                    val endDate = request["endDate"] as? String ?: "N/A"
                    val status = request["status"] as? String ?: "N/A"
                    val requestId = request["requestId"] as? String ?: ""
                    val employeeEmail = request["employeeEmail"] as? String ?: "" // 🔁 updated key

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text("👤 Employee: $employeeName",fontWeight = FontWeight.Bold , fontSize = 20.sp , modifier = Modifier.padding(bottom = 10.dp))
                            Text("📝 Reason: $reason", fontSize = 16.sp,style = MaterialTheme.typography.bodyMedium,modifier = Modifier.padding(bottom = 4.dp))
                            Text("📅 Start: $startDate", fontSize = 13.sp,style = MaterialTheme.typography.bodySmall,modifier = Modifier.padding(bottom = 4.dp))
                            Text("📅 End: $endDate", fontSize = 13.sp,style = MaterialTheme.typography.bodySmall,modifier = Modifier.padding(bottom = 4.dp))
                            Text("📌 Status: $status",fontSize = 13.sp, style = MaterialTheme.typography.labelMedium,modifier = Modifier.padding(bottom = 4.dp))

                            if (status == "pending") {
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.updateHolidayStatus(
                                                requestId = requestId,
                                                newStatus = "approved",
                                                employeeEmail = employeeEmail, // ✅ updated param
                                                message = "Your leave request has been approved",
                                                context = context
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Green
                                    ) {
                                        Text("Approve")
                                    }

                                    Button(
                                        onClick = {
                                            viewModel.updateHolidayStatus(
                                                requestId = requestId,
                                                newStatus = "declined",
                                                employeeEmail = employeeEmail, // ✅ updated param
                                                message = "Your leave request has been declined",
                                                context = context
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)) // Red
                                    ) {
                                        Text("Decline")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
