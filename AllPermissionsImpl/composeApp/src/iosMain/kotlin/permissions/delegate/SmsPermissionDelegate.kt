package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.Messages.didReceiveMessage
import platform.MessageUI.MFMessageComposeViewController
import platform.UIKit.UIApplication

internal class SmsPermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {
        return if (MFMessageComposeViewController.canSendText()) {
            PermissionState.GRANTED
        } else {
            PermissionState.DENIED
        }
    }

    override suspend fun providePermission() {
        val messageComposeViewController = MFMessageComposeViewController()
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(messageComposeViewController, true, null)
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}