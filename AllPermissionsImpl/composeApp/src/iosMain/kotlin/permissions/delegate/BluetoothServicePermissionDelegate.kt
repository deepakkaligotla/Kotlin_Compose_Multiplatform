package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openNSUrl
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.darwin.NSObject

internal class BluetoothServicePermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        return when {
            functions.isBluetoothEnabled() -> PermissionState.GRANTED
            !functions.isBluetoothEnabled() -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        openNSUrl("App-Prefs:root=Bluetooth")
    }
}
