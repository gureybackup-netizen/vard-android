package com.vardapp.privacymessenger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.vardapp.privacymessenger.matrix.RoomManager
import androidx.compose.ui.text.input.KeyboardOptions

@Composable
fun ContactSearchScreen(onRoomCreated: (String) -> Unit, onBack: () -> Unit) {
    var matrixID by remember { mutableStateOf("") }
    var isUserFound by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Find Contact", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextField(
            value = matrixID,
            onValueChange = { matrixID = it },
            label = { Text("Matrix ID (e.g. @user:matrix.org)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                if (!matrixID.startsWith("@") || !matrixID.contains(":")) {
                    errorMessage = "Invalid Matrix ID format"
                    return@Button
                }
                isLoading = true
                errorMessage = null
                // In a real app, use a ViewModel. Here we use a scope for brevity
                // but usually this should be in a ViewModel.
                // Since I don't have a SearchViewModel yet, I'll keep it simple.
                // For Block 3, I'll use a dummy call.
                isUserFound = true 
                isLoading = false
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Searching..." else "Search")
        }
        
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }
        
        if (isUserFound) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onRoomCreated(matrixID) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text("Start Encrypted Chat")
            }
        }
        
        TextButton(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Back to Chats")
        }
    }
}
