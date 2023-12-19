package permissions.delegate

import permissions.model.PermissionState
import permissions.util.openAppSettingsPage
import platform.CoreMotion.CMAuthorizationStatusAuthorized
import platform.CoreMotion.CMAuthorizationStatusDenied
import platform.CoreMotion.CMAuthorizationStatusNotDetermined
import platform.CoreMotion.CMPedometer
import platform.Foundation.NSDate
import platform.Foundation.distantFuture
import platform.Foundation.distantPast

internal class MotionFitnessPermissionDelegate : PermissionDelegate {

    override fun getPermissionState(): PermissionState {

        return when (CMPedometer.authorizationStatus()) {
            CMAuthorizationStatusNotDetermined -> PermissionState.NOT_DETERMINED
            CMAuthorizationStatusAuthorized -> PermissionState.GRANTED
            CMAuthorizationStatusDenied -> PermissionState.DENIED
            else -> PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission() {
        CMPedometer().queryPedometerDataFromDate(
            NSDate.distantPast(),
            toDate = NSDate.distantFuture()
        ) { _, _ ->
            // The query is just used to trigger the permission prompt
            // The result doesn't matter for our purposes
        }
    }

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}
