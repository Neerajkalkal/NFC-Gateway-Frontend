package com.example.nfc_gatway.screen.NotificationScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nfc_gatway.datastore.TokenManager
import com.example.nfc_gatway.datastore.decodeJwtToken
import com.example.nfc_gatway.viewmodels.NotificationViewModel.NotificationViewModel

@Composable
fun NotificationScreen(
    userType: String, // "admin" or "employee"
    navController : NavController,
    viewModel: NotificationViewModel = viewModel()

) {
    val context = LocalContext.current
    val token = TokenManager.getToken(context)
    val userInfo = token?.let { decodeJwtToken(it) }
    val email = userInfo?.get("email")?.toString() ?: ""
    val notifications = viewModel.notifications
    LaunchedEffect(email) {
        if (email.isNotEmpty()) {
            viewModel.fetchEmployeeNotificationsByEmail(email)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back" , tint = Color(0xFF014BD4))
            }
            Text("Notifications", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (notifications.isEmpty()) {
            Text("No notifications yet", color = Color.Gray)
        } else {
            LazyColumn {
                items(notifications) { notification ->
                    NotificationCard(notification)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: Map<String, Any>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf8f8f8)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = notification["title"].toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = notification["message"].toString(), fontSize = 14.sp)
        }
    }
}