package com.vardapp.privacymessenger.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vardapp.privacymessenger.matrix.RoomManager
import org.matrix.androidsdk.MXRoom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomListScreen(onNavigateToSearch: () -> Unit, onNavigateToChat: (String) -> Unit) {
    val rooms by RoomManager.instance.rooms.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chats") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToSearch) {
                Icon(Icons.Default.Add, contentDescription = "New Chat")
            }
        }
    ) { padding ->
        if (rooms.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No chats yet. Tap + to start one.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(rooms) { room ->
                    ListItem(
                        headlineContent = { Text(room.displayName ?: "Unknown Room") },
                        supportingContent = { Text(room.id) },
                        modifier = androidx.compose.foundation.clickable { onNavigateToChat(room.id) }
                    )
                }
            }
        }
    }
}
