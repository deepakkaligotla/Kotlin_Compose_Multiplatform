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

internal class ContactPermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, contactPermissions)
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(contactPermissions) {
            throw PermissionRequestException(Permission.CONTACT.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.CONTACT)
    }
}

private val contactPermissions: List<String> =
    listOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.GET_ACCOUNTS
    )