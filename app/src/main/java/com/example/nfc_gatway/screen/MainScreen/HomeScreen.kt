package com.example.nfc_gatway.screen.MainScreen

import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.Image
import com.example.nfc_gatway.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun HomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF26283B))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.nfc_logo), // your image
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Mary Smith", color = Color.White, fontSize = 20.sp)
                Text("SMS  415-555-1232", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("2", color = Color.White, fontSize = 18.sp)
                        Text("Unclaimed", color = Color.White, fontSize = 12.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$2,880", color = Color.White, fontSize = 18.sp)
                        Text("Monthly Earn", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF161928))
                .padding(16.dp)
        ){
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Mary Smith", color = Color.White, fontSize = 20.sp)
                Text("SMS  415-555-1232", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("2", color = Color.White, fontSize = 18.sp)
                        Text("Unclaimed", color = Color.White, fontSize = 12.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$2,880", color = Color.White, fontSize = 18.sp)
                        Text("Monthly Earn", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

