package functions

import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationDenied
import platform.CoreBluetooth.CBManagerAuthorizationNotDetermined
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.darwin.NSObject

fun isBluetoothEnabled(): Boolean {
    val cbCentralManager: CBCentralManager by lazy {
        CBCentralManager(
            object : NSObject(), CBCentralManagerDelegateProtocol {
                override fun centralManagerDidUpdateState(central: CBCentralManager) {}
            },
            null
        )
    }
    return when(CBCentralManager.authorization) {
        CBManagerAuthorizationAllowedAlways, CBManagerAuthorizationRestricted -> true
        CBManagerAuthorizationDenied, CBManagerAuthorizationNotDetermined -> false
        else -> false
    }
}