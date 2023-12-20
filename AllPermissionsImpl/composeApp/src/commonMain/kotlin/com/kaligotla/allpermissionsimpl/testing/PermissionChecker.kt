package com.kaligotla.allpermissionsimpl.testing

interface PermissionChecker {
    val isBluetoothEnabled: Boolean
    val isLocationEnabled: Boolean
    val isMobileDataEnabled: Boolean
    val isWifiEnabled: Boolean
}

expect fun getPermissionChecker(): PermissionChecker