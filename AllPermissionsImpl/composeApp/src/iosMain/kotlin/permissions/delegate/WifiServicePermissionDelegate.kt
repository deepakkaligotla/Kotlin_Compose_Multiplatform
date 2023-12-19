package permissions.delegate

import kotlinx.cinterop.*
import permissions.model.PermissionState
import permissions.util.openNSUrl
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.SystemConfiguration.*
import platform.darwin.os_unfair_lock_lock
import platform.darwin.os_unfair_lock_s
import platform.darwin.os_unfair_lock_trylock
import platform.darwin.os_unfair_lock_unlock

@OptIn(ExperimentalForeignApi::class)
internal class WifiServicePermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when {
            functions.isWiFiEnabled() -> PermissionState.GRANTED
            !functions.isWiFiEnabled() -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        openNSUrl("App-Prefs:WiFi")
    }
}

