package com.vardapp.privacymessenger.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.vardapp.privacymessenger.matrix.AuthViewModel
import com.vardapp.privacymessenger.matrix.RoomManager

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

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
                    scope.launch {
                        RoomManager.instance.createEncryptedDirectRoom(matrixID)
                    }
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
