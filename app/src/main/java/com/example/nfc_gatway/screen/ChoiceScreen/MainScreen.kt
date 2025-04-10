package com.example.nfc_gatway.screen.ChoiceScreen

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
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nfc_gatway.R

@Composable
fun MainScreen(
    onContinue:(isAdmin: Boolean) -> Unit
){
    var selectedAccount by remember { mutableStateOf("EMPLOYEE") }

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
                Column(modifier = Modifier.fillMaxSize().padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text("GETTING STARTED", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Welcome to NFC-Gateway.!!!", color = Color.White, fontSize = 17.sp)
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

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Choose an account type", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        Text("We’ll streamline your setup accordingly.", fontSize = 18.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(35.dp))

                        // Admin Button
                        AccountOptionButton(
                            label = "ADMIN",
                            icon = Icons.Default.Person,
                            isSelected = selectedAccount == "ADMIN",
                            onClick = { selectedAccount = "ADMIN" }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Employee Button
                        AccountOptionButton(
                            label = "EMPLOYEE",
                            icon = Icons.Default.Groups,
                            isSelected = selectedAccount == "EMPLOYEE",
                            onClick = { selectedAccount = "EMPLOYEE" }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Continue Button
                        Button(
                            onClick = { onContinue(selectedAccount == "ADMIN") },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
                        ) {
                            Text("Continue", color = Color.White ,fontSize = 18.sp)
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
        }
    }
}

@Composable
fun AccountOptionButton(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF3F51B5) else Color.LightGray.copy(alpha = 0.2f)
    val textColor = if (isSelected) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(30))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Icon(icon, contentDescription = null, tint = textColor , modifier = Modifier.size(35.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Text(label, color = textColor, fontWeight = FontWeight.Medium, fontSize = 18.sp )
        Spacer(modifier = Modifier.weight(3f))
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.White, unselectedColor = Color.Gray)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onContinue = { isAdmin ->
        // Just print or log for preview (no navigation in preview)
        println("Continue clicked. isAdmin: $isAdmin")
    })
}