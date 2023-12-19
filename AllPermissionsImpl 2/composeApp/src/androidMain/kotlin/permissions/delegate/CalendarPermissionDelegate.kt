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

internal class CalendarPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, calendarPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(calendarPermissions) {
            throw PermissionRequestException(Permission.CALENDAR.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.CALENDAR)
    }
}

private val calendarPermissions: List<String> =
    listOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
    )