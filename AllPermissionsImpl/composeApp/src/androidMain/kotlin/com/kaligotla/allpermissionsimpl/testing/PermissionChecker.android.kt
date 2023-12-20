package com.kaligotla.allpermissionsimpl.testing

import androidx.test.core.app.ApplicationProvider
import android.content.Context

class AndroidPermissionChecker(private val context: Context) : PermissionChecker {
    override val isBluetoothEnabled: Boolean
        get() = when {
            functions.isBluetoothEnabled(context) -> true
            !functions.isBluetoothEnabled(context) -> false
            else -> false
        }

    override val isLocationEnabled: Boolean
        get() = when {
            functions.isLocationEnabled(context) -> true
            !functions.isLocationEnabled(context) -> false
            else -> false
        }

    override val isMobileDataEnabled: Boolean
        get() = when {
            functions.isMobileDataEnabled(context) -> true
            !functions.isMobileDataEnabled(context) -> false
            else -> false
        }

    override val isWifiEnabled: Boolean
        get() = when {
            functions.isWifiEnabled(context) -> true
            !functions.isWifiEnabled(context) -> false
            else -> false
        }
}

actual fun getPermissionChecker(): PermissionChecker {
    return AndroidPermissionChecker(ApplicationProvider.getApplicationContext<Context>())
}