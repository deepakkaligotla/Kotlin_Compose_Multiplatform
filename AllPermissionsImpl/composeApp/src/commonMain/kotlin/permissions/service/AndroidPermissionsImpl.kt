package permissions.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import permissions.model.PermissionState
import permissions.model.Permission_Android
import permissions.util.getAndroidPermissionDelegate
import permissions.util.getPermissionDelegate

internal class AndroidPermissionsImpl : AndroidPermissions, KoinComponent {
    override fun checkAndroidPermission(permissionAndroid: Permission_Android): PermissionState {
        return try {
            return getAndroidPermissionDelegate(permissionAndroid).getPermissionState()
        } catch (e: Exception) {
            println("Failed to check permission $permissionAndroid")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override fun checkAndroidPermissionFlow(permissionAndroid: Permission_Android): Flow<PermissionState> {
        return flow {
            while (true) {
                val permissionState = checkAndroidPermission(permissionAndroid)
                emit(permissionState)
                delay(Permissions.PERMISSION_CHECK_FLOW_FREQUENCY)
            }
        }
    }

    override suspend fun provideAndroidPermission(permissionAndroid: Permission_Android) {
        try {
            getAndroidPermissionDelegate(permissionAndroid).providePermission()
        } catch (e: Exception) {
            println("Failed to request permission $permissionAndroid")
            e.printStackTrace()
        }
    }

    override fun openSettingPage(permissionAndroid: Permission_Android) {
        println("Open settings for permission $permissionAndroid")
        try {
            getAndroidPermissionDelegate(permissionAndroid).openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for permission $permissionAndroid")
            e.printStackTrace()
        }
    }
}