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

internal class SmsPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, smsPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(smsPermissions) {
            throw PermissionRequestException(Permission_Android.SMS.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission_Android.SMS)
    }
}

private val smsPermissions: List<String> =
    listOf(
        Manifest.permission.RECEIVE_MMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_WAP_PUSH,
        Manifest.permission.READ_SMS,
    )