package permissions.service

import kotlinx.coroutines.flow.Flow
import permissions.model.Permission_Android
import permissions.model.PermissionState

interface AndroidPermissions {
    fun checkAndroidPermission(permissionAndroid: Permission_Android): PermissionState
    fun checkAndroidPermissionFlow(permissionAndroid: Permission_Android): Flow<PermissionState>
    suspend fun provideAndroidPermission(permissionAndroid: Permission_Android)
    fun openSettingPage(permissionAndroid: Permission_Android)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}