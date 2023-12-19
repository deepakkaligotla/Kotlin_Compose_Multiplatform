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

internal class CallLogsPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, callLogsPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(callLogsPermissions) {
            throw PermissionRequestException(Permission_Android.CALL_LOGS.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission_Android.CALL_LOGS)
    }
}

private val callLogsPermissions: List<String> =
    listOf(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS
    )