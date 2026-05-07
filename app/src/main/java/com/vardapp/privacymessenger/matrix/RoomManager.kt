package com.vardapp.privacymessenger.matrix

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomManager private constructor() {
    private val sessionManager = SessionManager.instance

    private val _rooms = MutableStateFlow<List<Any>>(emptyList())
    val rooms: StateFlow<List<Any>> = _rooms.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    companion object {
        val instance by lazy { RoomManager() }
    }

    suspend fun searchUser(matrixID: String): Boolean {
        // Placeholder for Modern SDK Logic
        Log.d("RoomManager", "Searching for user $matrixID (Modern SDK)")
        return matrixID.startsWith("@") && matrixID.contains(":")
    }

    suspend fun createEncryptedDirectRoom(with: String): Any? {
        // Placeholder for Modern SDK Logic
        val session = sessionManager.currentSession
        Log.d("RoomManager", "Creating encrypted room with $with (Modern SDK)")
        return null
    }

    fun loadRooms() {
        // Modern SDK logic
    }

    fun startObservingRooms() {
        Log.d("RoomManager", "Starting to observe rooms (Modern SDK)")
    }

    fun stopObservingRooms() {
        Log.d("RoomManager", "Stopping room observation")
    }
}