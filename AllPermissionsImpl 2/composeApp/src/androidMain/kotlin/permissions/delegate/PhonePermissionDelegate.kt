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

internal class PhonePermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, phonePermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(phonePermissions) {
            throw PermissionRequestException(Permission.PHONE.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.PHONE)
    }
}

private val phonePermissions: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        listOf(
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCEPT_HANDOVER,
            Manifest.permission.USE_SIP,
            Manifest.permission.ADD_VOICEMAIL,
        )
    } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        listOf(
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCEPT_HANDOVER,
            Manifest.permission.USE_SIP,
            Manifest.permission.ADD_VOICEMAIL,
        )
    } else {
        emptyList()
    }