package com.kaligotla.testing

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import permissions.delegate.WifiServicePermissionDelegate
import platform.CoreTelephony.CTCellularData
import platform.CoreTelephony.CTCellularDataRestrictedState
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkFlagsReachable
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsIsWWAN
import platform.darwin.os_unfair_lock_s
import platform.darwin.os_unfair_lock_trylock
import platform.darwin.os_unfair_lock_unlock

class IOSPermissionChecker: PermissionChecker {
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
        get() = when(CTCellularData().restrictedState) {
            CTCellularDataRestrictedState.kCTCellularDataNotRestricted -> true
            CTCellularDataRestrictedState.kCTCellularDataRestricted, CTCellularDataRestrictedState.kCTCellularDataRestrictedStateUnknown -> false
            else -> false
        }

    override val isWifiEnabled: Boolean
        get() = when {
            functions.isWiFiEnabled() -> true
            !functions.isWiFiEnabled() -> false
            else -> false
        }
}

actual fun getPermissionChecker(): PermissionChecker = IOSPermissionChecker()