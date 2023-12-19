package permissions.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.model.Permission_Android
import permissions.util.PermissionRequestException
import permissions.util.checkPermissions
import permissions.util.openAppSettingsPage
import permissions.util.providePermissions

internal class PhysicalActivityPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, physicalActivityPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(physicalActivityPermissions) {
            throw PermissionRequestException(Permission_Android.PHYSICAL_ACTIVITY.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission_Android.PHYSICAL_ACTIVITY)
    }
}

private val physicalActivityPermissions: List<String> =
    listOf(
        Manifest.permission.ACTIVITY_RECOGNITION,
    )