package com.example.nfc_gatway.screen.ChatScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHomeScreen(
    onStartChat: () -> Unit
) {
    val tabs = listOf("CHATS", "STATUS", "CALLS")
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
//        backgroundColor = Color(0xFF0A0A0A),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onStartChat,
                containerColor = Color(0xFF2563EB),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "New Chat",
                    tint = Color.White
                )
            }
        },
        topBar = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack , contentDescription = "Back", tint = Color.Black)
            }
            TopAppBar(
//                backgroundColor = Color(0xFF0A0A0A),
                title = {
                    Text("NFC-Gatweway", color = Color.Black)
                },
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
                    }
                    IconButton(onClick = { /* More options */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.Black)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                tab,
                                color = if (selectedTab == index) Color.Blue else Color.White
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You haven’t chat yet",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onStartChat,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Start Chatting", color = Color.White ,)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
fun ChatHomeScreenPreview() {
    ChatHomeScreen(
        onStartChat = { /* Preview Action */ }
    )
}

