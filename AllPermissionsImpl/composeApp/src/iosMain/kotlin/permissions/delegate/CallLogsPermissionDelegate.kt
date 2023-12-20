package permissions.delegate

import permissions.model.PermissionState
import platform.CallKit.CXCallDirectoryManager
import platform.Foundation.NSError

internal class CallLogsPermissionDelegate: PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return try {
            CXCallDirectoryManager.sharedInstance.reloadExtensionWithIdentifier("YourCallDirectoryExtensionID") { error: NSError? ->
                if (error != null) {
                    println("Error reloading identification whitelist: ${error.localizedDescription}")
                } else {
                    println("Call log permission granted")
                }
            }
            PermissionState.GRANTED
        } catch (e: Exception) {
            println("Error providing call log permission: $e")
            PermissionState.DENIED
        }
    }

    override suspend fun providePermission() {

    }

    override fun openSettingPage() {
        openSettingPage()
    }
}