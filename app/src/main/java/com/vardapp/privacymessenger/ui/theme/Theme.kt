package com.vardapp.privacymessenger.ui.theme

import android.app.Application
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme()
private val LightColorScheme = lightColorScheme()

@Composable
fun PrivacyMessengerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
