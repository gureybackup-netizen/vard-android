package com.vardapp.privacymessenger.ui

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
            RoomListScreen(
                onNavigateToSearch = { navController.navigate("contact_search") },
                onNavigateToChat = { roomId -> navController.navigate("chat/$roomId") }
            )
        }
        composable("contact_search") {
            ContactSearchScreen(
                onRoomCreated = { matrixID -> 
                    // In a real app, we would call RoomManager.createEncryptedDirectRoom
                    navController.navigate("room_list") 
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("chat/{roomId}") { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            PlaceholderChatScreen(roomId)
        }
    }
}
