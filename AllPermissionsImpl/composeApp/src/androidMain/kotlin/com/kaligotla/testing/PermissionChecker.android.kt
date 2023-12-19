package com.kaligotla.testing

class AndroidPermissionChecker() : PermissionChecker {
    override val isBluetoothEnabled: Boolean
        get() = true

    override val isLocationEnabled: Boolean
        get() = true

    override val isMobileDataEnabled: Boolean
        get() = true

    override val isWifiEnabled: Boolean
        get() = true
}

actual fun getPermissionChecker(): PermissionChecker = AndroidPermissionChecker()