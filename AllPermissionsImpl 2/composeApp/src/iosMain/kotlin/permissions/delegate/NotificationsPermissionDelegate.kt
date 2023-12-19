package permissions.delegate

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UserNotifications.UNAuthorizationStatus
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined

internal class NotificationsPermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        val deferred = CompletableDeferred<UNAuthorizationStatus>()

        UNUserNotificationCenter.currentNotificationCenter().getNotificationSettingsWithCompletionHandler { settings ->
            deferred.complete(settings?.authorizationStatus ?: UNAuthorizationStatusNotDetermined)
        }

        return when (runBlocking { deferred.await() }) {
            UNAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            UNAuthorizationStatusAuthorized -> PermissionState.GRANTED
            UNAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun providePermission() {
        if (getPermissionState() == PermissionState.NOT_DETERMINED) {
            try {
                val result = suspendCancellableCoroutine<Boolean> { continuation ->
                    UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
                        ((1 shl 0) or (1 shl 1) or (1 shl 2)).toULong()
                    ) { granted, _ ->
                        continuation.resume(granted) {
                            print("Notifications permission granted")
                        }
                    }
                }

                if (!result) {
                    print("Notifications permission denied")
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
