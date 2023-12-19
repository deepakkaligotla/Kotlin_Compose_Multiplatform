package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openNSUrl
import platform.CoreLocation.CLLocationManager

internal class LocationServicePermissionDelegate : PermissionDelegate {
    private val locationManager = CLLocationManager()

    override fun getPermissionState(): PermissionState {
        return if (locationManager.locationServicesEnabled())
            PermissionState.GRANTED else PermissionState.DENIED
    }

    override suspend fun providePermission() {
        openNSUrl("App-Prefs:Privacy&path=LOCATION")
    }

    override fun openSettingPage() {
        openNSUrl("App-Prefs:Privacy&path=LOCATION")
    }
}
