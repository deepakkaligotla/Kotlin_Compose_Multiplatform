package permissions.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.util.checkPermissions
import permissions.util.openAppSettingsPage
import permissions.util.providePermissions

internal class LocationForegroundPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, fineLocationPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(fineLocationPermissions) {
            throw Exception(
                it.localizedMessage ?: "Failed to request foreground location permission"
            )
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.LOCATION_FOREGROUND)
    }
}

internal val fineLocationPermissions: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    } else {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
