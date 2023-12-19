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

internal class AudioMusicPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, audioMusicPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(audioMusicPermissions) {
            throw PermissionRequestException(Permission.AUDIO_MUSIC.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.AUDIO_MUSIC)
    }
}

private val audioMusicPermissions: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.READ_MEDIA_AUDIO
        )
    } else {
        emptyList()
    }