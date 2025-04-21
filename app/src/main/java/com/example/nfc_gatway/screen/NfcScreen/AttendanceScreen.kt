package com.example.nfc_gatway.screen.NfcScreen

import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.nfc_gatway.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.nfc_gatway.datastore.TokenManager
import com.example.nfc_gatway.viewmodels.AttendanceViewModel.AttendanceViewModel
import kotlinx.coroutines.delay

@Composable
fun AttendancePopupScreen(
    isVisible: Boolean = true,
    token: String,
    nfcId: String,
    viewModel: AttendanceViewModel = viewModel(),
    navController: NavHostController
) {
    var showSuccess by remember { mutableStateOf(false) }
    var hasScanned by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // ✅ Auto-trigger markAttendance on first visibility
    LaunchedEffect(isVisible, hasScanned) {
        if (isVisible && !hasScanned) {
            hasScanned = true
            val token = TokenManager.getToken(context)
            if (!token.isNullOrEmpty()) {
                viewModel.markAttendance(token, nfcId)
            }
        }
    }


    // ✅ Redirect after success
    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            delay(1500)
            navController.popBackStack()
        }
    }

    // ✅ UI remains the same
    if (isVisible && !showSuccess) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF3F51B5), Color(0xFF2196F3))
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Image(
                            painter = painterResource(id = R.drawable.nfctools),
                            contentDescription = "NFC Image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Hold near NFC tag",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Scanning for NFC automatically...",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}
