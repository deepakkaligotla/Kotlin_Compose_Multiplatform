package permissions.delegate

import kotlinx.coroutines.ExperimentalCoroutinesApi
import platform.MediaPlayer.MPMediaLibrary
import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.MediaPlayer.MPMediaLibraryAuthorizationStatusAuthorized
import platform.MediaPlayer.MPMediaLibraryAuthorizationStatusDenied
import platform.MediaPlayer.MPMediaLibraryAuthorizationStatusNotDetermined
import platform.MediaPlayer.MPMediaLibraryAuthorizationStatusRestricted

internal class MediaAndAppleMusicPermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (MPMediaLibrary.authorizationStatus()) {
            MPMediaLibraryAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            MPMediaLibraryAuthorizationStatusAuthorized,
            MPMediaLibraryAuthorizationStatusRestricted -> PermissionState.GRANTED
            MPMediaLibraryAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun providePermission() {
        if (MPMediaLibrary.authorizationStatus() == MPMediaLibraryAuthorizationStatusNotDetermined) {
            try {
                val result = suspendCancellableCoroutine<Boolean> { continuation ->
                    MPMediaLibrary.requestAuthorization { status ->
                        continuation.resume(status == MPMediaLibraryAuthorizationStatusAuthorized) {
                            print("Media & Apple Music permission granted")
                        }
                    }
                }

                if (!result) {
                    print("Media & Apple Music permission denied")
                }
            } catch (e: Exception) {
                // Handle exception if needed
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}
