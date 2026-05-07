package com.vardapp.privacymessenger.matrix

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.matrix.androidsdk.MXRoom
import org.matrix.androidsdk.MXRoomCreationParameters
import org.matrix.androidsdk.MXSession
import kotlin.coroutines.resume

class RoomManager private constructor() {
    private val sessionManager = SessionManager.instance

    private val _rooms = MutableStateFlow<List<MXRoom>>(emptyList())
    val rooms: StateFlow<List<MXRoom>> = _rooms.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    companion object {
        val instance by lazy { RoomManager() }
    }

    suspend fun searchUser(matrixID: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            Log.d("RoomManager", "Searching for user $matrixID")
            // Mocking searchUser
            continuation.resume(matrixID.startsWith("@") && matrixID.contains(":"))
        }
    }

    suspend fun createEncryptedDirectRoom(with: String): MXRoom? {
        val session = sessionManager.currentSession ?: return null
        return suspendCancellableCoroutine { continuation ->
            Log.d("RoomManager", "Creating encrypted room with $with")
            
            val params = MXRoomCreationParameters().apply {
                enableEncryption = true // K6: CRITICAL
            }
            
            // Mocking room creation
            // In reality: session.createRoom(params, ...)
            continuation.resume(null) // Replace with actual MXRoom when implemented
        }
    }

    fun loadRooms() {
        val session = sessionManager.currentSession ?: return
        _rooms.value = session.rooms
    }

    fun startObservingRooms() {
        Log.d("RoomManager", "Starting to observe rooms")
        // Register for new room notifications here
    }

    fun stopObservingRooms() {
        Log.d("RoomManager", "Stopping room observation")
    }
}
