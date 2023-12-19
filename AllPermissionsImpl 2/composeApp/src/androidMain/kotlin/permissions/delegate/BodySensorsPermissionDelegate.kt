package permissions.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.model.Permission_Android
import permissions.util.PermissionRequestException
import permissions.util.checkPermissions
import permissions.util.openAppSettingsPage
import permissions.util.providePermissions

internal class BodySensorsPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, bodySensorPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(bodySensorPermissions) {
            throw PermissionRequestException(Permission_Android.BODY_SENSOR.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission_Android.BODY_SENSOR)
    }
}

private val bodySensorPermissions: List<String> =
    listOf(
        Manifest.permission.BODY_SENSORS,
    )