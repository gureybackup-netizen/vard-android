package com.vardapp.privacymessenger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vardapp.privacymessenger.matrix.AuthViewModel

@Composable
fun RegistrationScreen(viewModel: AuthViewModel, onNavigateToLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Account", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username (e.g. yourname)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, capitalization = KeyboardCapitalization.None)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, capitalization = KeyboardCapitalization.None)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, capitalization = KeyboardCapitalization.None)
        )
        
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(vertical = 8.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { viewModel.register(username, password) },
            enabled = !isLoading && username.isNotEmpty() && password.isNotEmpty() && password == confirmPassword,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(if (isLoading) "Registering..." else "Register")
        }
        
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}
