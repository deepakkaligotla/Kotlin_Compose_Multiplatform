package permissions.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.util.PermissionRequestException
import permissions.util.checkPermissions
import permissions.util.openAppSettingsPage
import permissions.util.providePermissions

internal class NotificationPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, notificationPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(notificationPermissions) {
            throw PermissionRequestException(Permission.NOTIFICATION.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.NOTIFICATION)
    }
}

private val notificationPermissions: List<String> =
    listOf(
        Manifest.permission.POST_NOTIFICATIONS,
    )