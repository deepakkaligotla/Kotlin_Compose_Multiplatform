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

internal class PhotosVideosPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, photosVideosPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(photosVideosPermissions) {
            throw PermissionRequestException(Permission.PHOTO.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.PHOTO)
    }
}

private val photosVideosPermissions: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf( Manifest.permission.ACCESS_MEDIA_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
            )
        } else {
            TODO("VERSION.SDK_INT < TIRAMISU")
        }
    } else {
        TODO("VERSION.SDK_INT < Q")
    }