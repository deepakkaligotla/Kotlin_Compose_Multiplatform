package permissions.delegate

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import permissions.model.PermissionState
import permissions.util.openNSUrl
import platform.CoreFoundation.CFDictionaryGetValue
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.CFStringGetCStringPtr
import platform.CoreFoundation.CFStringRef
import platform.SystemConfiguration.CNCopyCurrentNetworkInfo
import platform.SystemConfiguration.kCNNetworkInfoKeySSID
import platform.SystemConfiguration.kCNNetworkInfoKeyBSSID

internal class HotspotPermissionDelegate : PermissionDelegate {

    companion object {
        private const val INTERFACE_NAME = "en0"
        private const val HOTSPOT_SETTINGS_URL = "App-Prefs:Hotspot"
    }

    override fun getPermissionState(): PermissionState {
        return when {
            isHotspotEnabled() -> PermissionState.GRANTED
            !isHotspotEnabled() -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        openNSUrl(HOTSPOT_SETTINGS_URL)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun isHotspotEnabled(): Boolean {
        val interfaceName: CFStringRef = CFStringCreateWithCString(null, INTERFACE_NAME, 0u)!!
        val currentNetworkInfo: CFDictionaryRef? = CNCopyCurrentNetworkInfo(interfaceName)
        CFRelease(interfaceName)

        if (currentNetworkInfo != null) {
            try {
                val ssidRef: CFStringRef = CFDictionaryGetValue(currentNetworkInfo, kCNNetworkInfoKeySSID) as CFStringRef
                val bssidRef: CFStringRef = CFDictionaryGetValue(currentNetworkInfo, kCNNetworkInfoKeyBSSID) as CFStringRef

                val ssid = ssidRef.let { CFStringGetCStringPtr(it, 0u)?.toKString() }
                val bssid = bssidRef.let { CFStringGetCStringPtr(it, 0u)?.toKString() }

                return !ssid.isNullOrBlank() && !bssid.isNullOrBlank()
            } finally {
                CFRelease(currentNetworkInfo)
            }
        }

        return false
    }
}