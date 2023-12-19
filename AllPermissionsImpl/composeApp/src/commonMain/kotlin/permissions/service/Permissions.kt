package permissions.service

import permissions.model.Permission
import permissions.model.PermissionState
import kotlinx.coroutines.flow.Flow

interface Permissions {
    fun checkPermission(permission: Permission): PermissionState
    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
    suspend fun providePermission(permission: Permission)
    fun openSettingPage(permission: Permission)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}
