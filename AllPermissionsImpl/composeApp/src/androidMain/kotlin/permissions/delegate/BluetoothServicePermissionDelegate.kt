package permissions.delegate

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.provider.Settings
import permissions.util.openPage
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.model.Service
import permissions.util.CannotOpenSettingsException

internal class BluetoothServicePermissionDelegate(
    private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter?,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return if (bluetoothAdapter?.isEnabled == true)
            PermissionState.GRANTED else PermissionState.DENIED
    }

    override suspend fun providePermission() {
        openSettingPage()
    }

    override fun openSettingPage() {
        context.openPage(
            action = Settings.ACTION_BLUETOOTH_SETTINGS,
            onError = { throw CannotOpenSettingsException(Service.BLUETOOTH_SERVICE_ON.name) }
        )
    }
}
