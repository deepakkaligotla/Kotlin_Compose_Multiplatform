package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.Photos.PHPhotoLibrary
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted

internal class PhotoPermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        return when (PHPhotoLibrary.authorizationStatus()) {
            PHAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            PHAuthorizationStatusAuthorized, PHAuthorizationStatusRestricted -> PermissionState.GRANTED
            PHAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        PHPhotoLibrary.requestAuthorization { status ->
            if (status == PHAuthorizationStatusAuthorized) {
                println("granted Photos permission")
            } else {
                println("denied Photos permission")
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}