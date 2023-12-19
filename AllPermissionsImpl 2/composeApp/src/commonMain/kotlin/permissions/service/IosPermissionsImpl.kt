package permissions.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import permissions.model.PermissionState
import permissions.model.Permission_iOS
import permissions.util.getIosPermissionDelegate
import permissions.util.getPermissionDelegate

internal class IosPermissionsImpl : IosPermissions, KoinComponent {
    override fun checkIosPermission(permissionIos: Permission_iOS): PermissionState {
        return try {
            return getIosPermissionDelegate(permissionIos).getPermissionState()
        } catch (e: Exception) {
            println("Failed to check permission $permissionIos")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override fun checkIosPermissionFlow(permissionIos: Permission_iOS): Flow<PermissionState> {
        return flow {
            while (true) {
                val permissionState = checkIosPermission(permissionIos)
                emit(permissionState)
                delay(Permissions.PERMISSION_CHECK_FLOW_FREQUENCY)
            }
        }
    }

    override suspend fun provideIosPermission(permissionIos: Permission_iOS) {
        try {
            getIosPermissionDelegate(permissionIos).providePermission()
        } catch (e: Exception) {
            println("Failed to request permission $permissionIos")
            e.printStackTrace()
        }
    }

    override fun openSettingPage(permissionIos: Permission_iOS) {
        println("Open settings for permission $permissionIos")
        try {
            getIosPermissionDelegate(permissionIos).openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for permission $permissionIos")
            e.printStackTrace()
        }
    }
}