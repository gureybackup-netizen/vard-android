package com.vardapp.privacymessenger.matrix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                // Modern Rust SDK Register Flow
                Log.d("AuthViewModel", "Registering user (Rust): $username")
                // client.register(username, password)
                // For now, simulating success
                login(username, password)
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
                // Modern Rust SDK Login Flow
                val result = MatrixManager.instance.login(username, password)
                result.onSuccess {
                    sessionManager.saveCredentials(username, password)
                    _isLoggedIn.value = true
                    _currentUserID.value = username
                }.onFailure { e ->
                    _errorMessage.value = mapMatrixError(e.message)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Login failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        sessionManager.clearCredentials()
        _isLoggedIn.value = false
        _currentUserID.value = null
    }

    fun restoreSession() {
        viewModelScope.launch {
            val creds = sessionManager.loadCredentials()
            if (creds.first != null) {
                login(creds.first!!, creds.second!!)
            }
        }
    }

    private fun mapMatrixError(error: String?): String {
        return when {
            error?.contains("M_USER_IN_USE") == true -> "Username is already taken"
            error?.contains("M_FORBIDDEN") == true -> "Invalid credentials"
            else -> "An unexpected error occurred"
        }
    }
}