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

    private val wifiCheckLock: CPointer<os_unfair_lock_s> = nativeHeap.alloc<os_unfair_lock_s>().ptr

    private companion object {
        private const val GOOGLE_CHECK_URL = "www.google.co.in"
    }

    init {
        os_unfair_lock_lock(wifiCheckLock)
        os_unfair_lock_unlock(wifiCheckLock)
    }

    override fun getPermissionState(): PermissionState {
        return when {
            isWiFiEnabled() -> PermissionState.GRANTED
            !isWiFiEnabled() -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        openNSUrl("App-Prefs:WiFi")
    }

    private fun isWiFiEnabled(): Boolean {
        os_unfair_lock_trylock(wifiCheckLock)
        var isWiFiEnabled = false

        val reachability = SCNetworkReachabilityCreateWithName(null, GOOGLE_CHECK_URL)
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
}

