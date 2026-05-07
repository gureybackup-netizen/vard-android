package com.vardapp.privacymessenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.vardapp.privacymessenger.matrix.AuthViewModel
import com.vardapp.privacymessenger.matrix.SessionManager
import com.vardapp.privacymessenger.ui.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SessionManager.init(this)
        val authViewModel = AuthViewModel(SessionManager.instance)

        setContent {
            var isLoggedIn by authViewModel.isLoggedIn.collectAsState()

            LaunchedEffect(Unit) {
                authViewModel.restoreSession()
            }

            if (isLoggedIn) {
                AppNavigation(authViewModel)
            } else {
                AppNavigation(authViewModel)
            }
        }
    }
}