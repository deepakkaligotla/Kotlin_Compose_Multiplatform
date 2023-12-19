package permissions.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.util.PermissionRequestException
import permissions.util.checkPermissions
import permissions.util.openAppSettingsPage
import permissions.util.providePermissions

internal class CameraPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, cameraPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(cameraPermissions) {
            throw PermissionRequestException(Permission.CAMERA.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.CAMERA)
    }
}

private val cameraPermissions: List<String> =
    listOf(
        Manifest.permission.CAMERA,
    )