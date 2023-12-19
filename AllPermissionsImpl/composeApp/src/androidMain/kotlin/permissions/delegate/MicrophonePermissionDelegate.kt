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

internal class MicrophonePermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, microphonePermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(microphonePermissions) {
            throw PermissionRequestException(Permission.MICROPHONE.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.MICROPHONE)
    }
}

private val microphonePermissions: List<String> =
    listOf(
        Manifest.permission.RECORD_AUDIO,
    )