package permissions.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import permissions.model.Service
import permissions.model.PermissionState
import permissions.util.getPermissionDelegate
import permissions.util.getServiceDelegate

internal class ServicesImpl : Services, KoinComponent {
    override fun checkService(service: Service): PermissionState {
        return try {
            return getServiceDelegate(service).getPermissionState()
        } catch (e: Exception) {
            println("Failed to check permission $service")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override fun checkServiceFlow(service: Service): Flow<PermissionState> {
        return flow {
            while (true) {
                val permissionState = checkService(service)
                emit(permissionState)
                delay(Permissions.PERMISSION_CHECK_FLOW_FREQUENCY)
            }
        }
    }

    override suspend fun provideService(service: Service) {
        try {
            getServiceDelegate(service).providePermission()
        } catch (e: Exception) {
            println("Failed to request service $service")
            e.printStackTrace()
        }
    }

    override fun openSettingPage(service: Service) {
        println("Open settings for service $service")
        try {
            getServiceDelegate(service).openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for service $service")
            e.printStackTrace()
        }
    }
}