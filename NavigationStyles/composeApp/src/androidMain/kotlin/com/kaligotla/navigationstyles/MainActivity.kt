package com.kaligotla.navigationstyles

import App
import Navigator
import NavigatorHandler
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private val navigatorHandler: NavigatorHandler = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(navigatorHandler)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val navigatorHandler: NavigatorHandler = Navigator()
    App(navigatorHandler)
}