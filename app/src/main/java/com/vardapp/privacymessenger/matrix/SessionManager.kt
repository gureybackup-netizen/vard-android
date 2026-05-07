package com.vardapp.privacymessenger.matrix

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.matrix.androidsdk.MXSession

class SessionManager private constructor() {
    private lateinit var sharedPreferences: SharedPreferences
    var currentSession: MXSession? = null
        private set

    companion object {
        private const val MASTER_KEY_ALIAS = "com.vardapp.privacymessenger.master_key"
        private const val PREFS_FILE_NAME = "secure_prefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_ACCESS_TOKEN = "access_token"

        val instance by lazy { SessionManager() }
    }

    fun init(context: Context) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_SIV)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveCredentials(username: String, password: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun loadCredentials(): Pair<String?, String?> {
        val username = sharedPreferences.getString(KEY_USERNAME, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        return Pair(username, password)
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun loadAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearCredentials() {
        sharedPreferences.edit().clear().apply()
        currentSession = null
    }

    fun setSession(session: MXSession?) {
        this.currentSession = session
    }
}
