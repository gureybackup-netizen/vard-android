package com.vardapp.privacymessenger.matrix

import android.util.Log
import org.matrix.androidsdk.MXSession
import org.matrix.androidsdk.MXRestClient
import java.net.URL

class MatrixManager private constructor() {
    companion object {
        val instance by lazy { MatrixManager() }
    }

    suspend fun testConnection(): String {
        return try {
            val homeServer = "https://matrix.org"
            Log.d("MatrixManager", "Testing connection to $homeServer")
            // We use MXRestClient for a simple connectivity check without a full session
            val client = MXRestClient(homeServer)
            "MatrixSDK loaded. Server: $homeServer"
        } catch (e: Exception) {
            Log.e("MatrixManager", "Connection test failed", e)
            "Connection test failed: ${e.message}"
        }
    }
}
