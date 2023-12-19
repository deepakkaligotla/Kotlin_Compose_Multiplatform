package permissions.service

import kotlinx.coroutines.flow.Flow
import permissions.model.PermissionState
import permissions.model.Service

interface Services {
    fun checkService(service: Service): PermissionState
    fun checkServiceFlow(service: Service): Flow<PermissionState>
    suspend fun provideService(service: Service)
    fun openSettingPage(service: Service)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}