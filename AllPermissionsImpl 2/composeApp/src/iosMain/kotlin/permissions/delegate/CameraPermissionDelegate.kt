package permissions.delegate

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType

internal class CameraPermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
            AVAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            AVAuthorizationStatusAuthorized, AVAuthorizationStatusRestricted -> PermissionState.GRANTED
            AVAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun providePermission() {
        if (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) == AVAuthorizationStatusNotDetermined) {
            val result = suspendCancellableCoroutine<Boolean> { continuation ->
                AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted: Boolean ->
                    continuation.resume(granted) {
                        println("Camera permission granted")
                    }
                }
            }

            if (!result) {
                println("Camera permission denied")
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}