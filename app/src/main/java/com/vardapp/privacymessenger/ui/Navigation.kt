package com.vardapp.privacymessenger.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vardapp.privacymessenger.matrix.AuthViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(authViewModel, onNavigateToRegister = { navController.navigate("register") })
        }
        composable("register") {
            RegistrationScreen(authViewModel, onNavigateToLogin = { navController.navigate("login") })
        }
        composable("room_list") {
            Text("Room List Screen (Block 3)")
        }
    }
}
