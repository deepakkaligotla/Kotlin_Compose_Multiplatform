package permissions.service

import kotlinx.coroutines.flow.Flow
import permissions.model.PermissionState
import permissions.model.Permission_iOS

interface IosPermissions {
    fun checkIosPermission(permissionIos: Permission_iOS): PermissionState
    fun checkIosPermissionFlow(permissionIos: Permission_iOS): Flow<PermissionState>
    suspend fun provideIosPermission(permissionIos: Permission_iOS)
    fun openSettingPage(permissionIos: Permission_iOS)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}