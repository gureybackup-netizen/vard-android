package com.vardapp.privacymessenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.vardapp.privacymessenger.matrix.MatrixManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var status by remember { mutableStateOf("Initializing...") }
            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                status = MatrixManager.instance.testConnection()
            }

            Text(text = status)
        }
    }
}
