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

internal class BodySensorBackgroundPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
    private val bodySensorPermissionDelegate: PermissionDelegate,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (bodySensorPermissionDelegate.getPermissionState()) {
            PermissionState.GRANTED ->
                checkPermissions(context, activity, bodySensorPermissions)

            PermissionState.DENIED,
            PermissionState.NOT_DETERMINED,
            -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(bodySensorPermissions) {
            throw Exception(
                it.localizedMessage ?: "Failed to request background body sensor permission"
            )
        }
        getPermissionState()
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.LOCATION_BACKGROUND)
    }
}

private val bodySensorPermissions: List<String> =
    listOf(Manifest.permission.BODY_SENSORS_BACKGROUND)