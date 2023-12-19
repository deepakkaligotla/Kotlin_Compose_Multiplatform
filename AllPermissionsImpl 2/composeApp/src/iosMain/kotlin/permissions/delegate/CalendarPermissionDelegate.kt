package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.EventKit.EKAuthorizationStatusAuthorized
import platform.EventKit.EKAuthorizationStatusDenied
import platform.EventKit.EKAuthorizationStatusNotDetermined
import platform.EventKit.EKAuthorizationStatusRestricted
import platform.EventKit.EKEntityType
import platform.EventKit.EKEventStore

internal class CalendarPermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (EKEventStore.authorizationStatusForEntityType(EKEntityType.EKEntityTypeEvent)) {
            EKAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            EKAuthorizationStatusAuthorized, EKAuthorizationStatusRestricted -> PermissionState.GRANTED
            EKAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        EKEventStore().requestFullAccessToEventsWithCompletion { granted, nsError ->
            if (granted) {
                print("granted Calendar permission")
            } else {
                print("denied Calendar permission")
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}