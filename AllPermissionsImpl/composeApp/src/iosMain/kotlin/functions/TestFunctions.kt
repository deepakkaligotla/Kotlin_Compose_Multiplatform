package functions

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationDenied
import platform.CoreBluetooth.CBManagerAuthorizationNotDetermined
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.CoreLocation.CLLocationManager
import platform.CoreTelephony.CTCellularData
import platform.CoreTelephony.CTCellularDataRestrictedState
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkFlagsReachable
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsIsWWAN
import platform.darwin.NSObject
import platform.darwin.os_unfair_lock_lock
import platform.darwin.os_unfair_lock_s
import platform.darwin.os_unfair_lock_trylock
import platform.darwin.os_unfair_lock_unlock

fun isBluetoothEnabled(): Boolean {
    return when(CBCentralManager.authorization) {
        CBManagerAuthorizationAllowedAlways, CBManagerAuthorizationRestricted -> true
        CBManagerAuthorizationDenied, CBManagerAuthorizationNotDetermined -> false
        else -> false
    }
}

fun isLocationEnabled(): Boolean {
    val locationManager = CLLocationManager()
    return locationManager.locationServicesEnabled
}

fun isMobileDataEnabled(): Boolean {
    return when(CTCellularData().restrictedState) {
        CTCellularDataRestrictedState.kCTCellularDataNotRestricted -> true
        CTCellularDataRestrictedState.kCTCellularDataRestricted, CTCellularDataRestrictedState.kCTCellularDataRestrictedStateUnknown -> false
        else -> false
    }
}

@OptIn(ExperimentalForeignApi::class)
fun isWiFiEnabled(): Boolean {
    val wifiCheckLock: CPointer<os_unfair_lock_s> = nativeHeap.alloc<os_unfair_lock_s>().ptr

    os_unfair_lock_lock(wifiCheckLock)
    os_unfair_lock_unlock(wifiCheckLock)

    os_unfair_lock_trylock(wifiCheckLock)
    var isWiFiEnabled = false

    val reachability = SCNetworkReachabilityCreateWithName(null, "www.google.co.in")
    if (reachability != null) {
        val flags = memScoped {
            val flagsVar = alloc<UIntVar>()
            if (SCNetworkReachabilityGetFlags(reachability, flagsVar.ptr)) {
                isWiFiEnabled = hasConnectivity(flagsVar.value.toInt())
            }
        }
    } else {
    }

    os_unfair_lock_unlock(wifiCheckLock)
    return isWiFiEnabled
}

private fun hasConnectivity(flags: Int): Boolean {
    return try {
        (flags and ((kSCNetworkFlagsReachable or kSCNetworkReachabilityFlagsIsWWAN).toInt())).toUInt() == kSCNetworkFlagsReachable
    } catch (e: Exception) {
        false
    }
}