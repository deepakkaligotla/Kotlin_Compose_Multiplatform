package permissions.delegate

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.AppTrackingTransparency.ATTrackingManager
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusAuthorized
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusDenied
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusNotDetermined

internal class AppTrackingTransparencyPermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        return when (ATTrackingManager.trackingAuthorizationStatus) {
            ATTrackingManagerAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            ATTrackingManagerAuthorizationStatusAuthorized -> PermissionState.GRANTED
            ATTrackingManagerAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun providePermission() {
        if (ATTrackingManager.trackingAuthorizationStatus == ATTrackingManagerAuthorizationStatusNotDetermined) {
            suspendCancellableCoroutine<Boolean> { continuation ->
                ATTrackingManager.requestTrackingAuthorizationWithCompletionHandler { status ->
                    val isPermissionGranted = status == ATTrackingManagerAuthorizationStatusAuthorized
                    continuation.resume(isPermissionGranted) {
                        if (isPermissionGranted) {
                            print("ATT permission granted")
                        } else {
                            println("ATT permission denied")
                        }
                    }
                }
            }
        } else {
            println("ATT permission denied")
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}