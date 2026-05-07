package com.vardapp.privacymessenger.matrix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.matrix.androidsdk.MXSession
import org.matrix.androidsdk.MXSession.SessionCallback
import org.matrix.androidsdk.MXFileStore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUserID = MutableStateFlow<String?>(null)
    val currentUserID: StateFlow<String?> = _currentUserID.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = suspendCancellableCoroutine<Boolean> { continuation ->
                    // Mocking register call as Matrix SDK register is usually a REST call
                    // In a real implementation, we would use MXRestClient.register
                    Log.d("AuthViewModel", "Registering user $username")
                    continuation.resume(true)
                }
                if (result) {
                    login(username, password)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Registration failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val accessToken = suspendCancellableCoroutine<String> { continuation ->
                    // Mocking login call
                    Log.d("AuthViewModel", "Logging in user $username")
                    continuation.resume("mock_access_token")
                }
                sessionManager.saveCredentials(username, password)
                sessionManager.saveAccessToken(accessToken)
                startSession()
            } catch (e: Exception) {
                _errorMessage.value = mapMatrixError(e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun startSession() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val session = suspendCancellableCoroutine<MXSession> { continuation ->
                    // In reality, we would initialize MXSession here
                    // K7: enableCrypto() before start()
                    // K9: MXFileStore for persistence
                    Log.d("AuthViewModel", "Starting session")
                    // Mocking session creation
                    continuation.resume(MXSession.getInstance()) 
                }
                sessionManager.setSession(session)
                _isLoggedIn.value = true
                _currentUserID.value = session.userId
            } catch (e: Exception) {
                _errorMessage.value = "Session start failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun restoreSession() {
        viewModelScope.launch {
            val creds = sessionManager.loadCredentials()
            if (creds.first != null) {
                startSession()
            }
        }
    }

    fun logout() {
        sessionManager.clearCredentials()
        _isLoggedIn.value = false
        _currentUserID.value = null
    }

    private fun mapMatrixError(error: String?): String {
        return when (error) {
            "M_USER_IN_USE" -> "Username is already taken"
            "M_FORBIDDEN" -> "Access forbidden"
            "M_LIMIT_EXCEEDED" -> "Too many requests"
            "M_NOT_FOUND" -> "User not found"
            else -> "An unexpected error occurred"
        }
    }
}
