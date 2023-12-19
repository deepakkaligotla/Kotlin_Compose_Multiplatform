package permissions.delegate

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.Log
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.model.Service
import permissions.util.CannotOpenSettingsException
import permissions.util.openPage

internal class MobileDataServicePermissionDelegate(
    private val context: Context,
    private val connectivityManager: ConnectivityManager
) : PermissionDelegate {

    private companion object {
        private const val NETWORK_OPERATOR_SETTINGS_ACTION = Settings.ACTION_NETWORK_OPERATOR_SETTINGS
    }

    override fun getPermissionState(): PermissionState {
        return when {
            connectivityManager.isDefaultNetworkActive -> PermissionState.GRANTED
            !connectivityManager.isDefaultNetworkActive -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        try {
            context.openPage(
                action = NETWORK_OPERATOR_SETTINGS_ACTION,
                onError = { throw CannotOpenSettingsException(Service.MOBILE_DATA_SERVICE_ON.name) }
            )
        } catch (e: CannotOpenSettingsException) {
            Log.e("MobileDataServicePermissionDelegate", "Error opening settings page for ${Service.MOBILE_DATA_SERVICE_ON.name}", e)
        }
    }

    private fun isMobileDataEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return networkInfo?.isConnected == true
        }
    }
}
