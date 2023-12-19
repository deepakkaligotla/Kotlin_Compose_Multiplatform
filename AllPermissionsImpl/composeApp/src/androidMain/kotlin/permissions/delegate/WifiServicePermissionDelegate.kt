package permissions.delegate

import android.content.Context
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.provider.Settings
import permissions.model.PermissionState
import permissions.model.Service
import permissions.util.CannotOpenSettingsException
import permissions.util.openPage

internal class WifiServicePermissionDelegate(
    private val context: Context,
    private val wifiManager: WifiManager,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when {
            wifiManager.isWifiEnabled -> PermissionState.GRANTED
            !wifiManager.isWifiEnabled -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        context.openPage(
            action = Settings.ACTION_WIFI_SETTINGS,
            onError = { throw CannotOpenSettingsException(Service.WIFI_SERVICE_ON.name) }
        )
    }
}