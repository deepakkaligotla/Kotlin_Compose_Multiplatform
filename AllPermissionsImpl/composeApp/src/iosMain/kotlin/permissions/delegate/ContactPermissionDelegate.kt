package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.Contacts.CNContactStore
import platform.Contacts.CNAuthorizationStatusAuthorized
import platform.Contacts.CNAuthorizationStatusDenied
import platform.Contacts.CNAuthorizationStatusNotDetermined
import platform.Contacts.CNAuthorizationStatusRestricted
import platform.Contacts.CNEntityType

internal class ContactPermissionDelegate : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return when (CNContactStore.authorizationStatusForEntityType(CNEntityType.CNEntityTypeContacts)) {
            CNAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            CNAuthorizationStatusAuthorized, CNAuthorizationStatusRestricted -> PermissionState.GRANTED
            CNAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        CNContactStore().requestAccessForEntityType(CNEntityType.CNEntityTypeContacts) { granted, nsError ->
            if (granted) {
                println("granted Contacts permission")
            } else {
                println("denied Contacts permission")
            }
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}