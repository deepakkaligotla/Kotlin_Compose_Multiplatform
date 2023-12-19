package com.kaligotla.test

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
            ) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}