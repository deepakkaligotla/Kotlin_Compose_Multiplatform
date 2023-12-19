package com.kaligotla.testing

class AndroidPermissionChecker() : PermissionChecker {
    override val isBluetoothEnabled: Boolean
        get() = when {
            functions.isBluetoothEnabled() -> true
            !functions.isBluetoothEnabled() -> false
            else -> false
        }

    override val isLocationEnabled: Boolean
        get() = when {
            functions.isLocationEnabled() -> true
            !functions.isLocationEnabled() -> false
            else -> false
        }

    override val isMobileDataEnabled: Boolean
        get() = when {
            functions.isMobileDataEnabled() -> true
            !functions.isMobileDataEnabled() -> false
            else -> false
        }

    override val isWifiEnabled: Boolean
        get() = when {
            functions.isWifiEnabled() -> true
            !functions.isWifiEnabled() -> false
            else -> false
        }
}

actual fun getPermissionChecker(): PermissionChecker = AndroidPermissionChecker()