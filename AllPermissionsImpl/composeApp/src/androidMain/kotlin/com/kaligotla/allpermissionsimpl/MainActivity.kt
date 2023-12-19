package com.kaligotla.allpermissionsimpl

import App
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val koinApplication = initKoin(
            additionalModules = listOf(
                module {
                    single<Context> { applicationContext }
                    single<Activity> { this@MainActivity }
                }
            )
        )

        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
            ) {
                App(
                    koin = koinApplication.koin
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val koinApplication = initKoin(
        additionalModules = listOf(
            module {
                single<Context> { androidContext() }
                single<Activity> { Activity() }
            }
        )
    )
    App(koin = koinApplication.koin)
}