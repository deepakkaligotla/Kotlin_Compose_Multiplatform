package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openNSUrl
import platform.CoreTelephony.CTCellularData
import platform.CoreTelephony.CTCellularDataRestrictedState

internal class MobileDataServicePermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when {
            isMobileDataEnabled() -> PermissionState.GRANTED
            !isMobileDataEnabled() -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openNSUrl("App-Prefs:MobileData")
    }

    override fun openSettingPage() {
        openNSUrl("App-Prefs:MobileData")
    }

    private fun isMobileDataEnabled(): Boolean {
        return try {
            val cellularData = CTCellularData()
            cellularData.restrictedState == CTCellularDataRestrictedState.kCTCellularDataNotRestricted
        } catch (e: Exception) {
            false
        }
    }
}