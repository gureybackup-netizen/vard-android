package com.vardapp.privacymessenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.vardapp.privacymessenger.matrix.AuthViewModel
import com.vardapp.privacymessenger.matrix.SessionManager
import com.vardapp.privacymessenger.ui.AppNavigation
import com.vardapp.privacymessenger.ui.RoomListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SessionManager.instance.init(this)
        val authViewModel = AuthViewModel(SessionManager.instance)

        setContent {
            val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

            LaunchedEffect(Unit) {
                authViewModel.restoreSession()
            }

if (isLoggedIn) {
                  RoomListScreen(
                      onNavigateToSearch = { /* TODO */ },
                      onNavigateToChat = { /* TODO */ }
                  )
            } else {
                AppNavigation(authViewModel)
            }
        }
    }
}