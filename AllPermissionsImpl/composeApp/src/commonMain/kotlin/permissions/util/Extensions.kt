package permissions.util

import permissions.delegate.PermissionDelegate
import permissions.model.Permission
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import permissions.model.Permission_Android
import permissions.model.Permission_iOS
import permissions.model.Service

internal fun KoinComponent.getServiceDelegate(service: Service): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(service.name))
    return permissionDelegate
}

internal fun KoinComponent.getPermissionDelegate(permission: Permission): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
    return permissionDelegate
}

internal fun KoinComponent.getAndroidPermissionDelegate(permissionAndroid: Permission_Android): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(permissionAndroid.name))
    return permissionDelegate
}

internal fun KoinComponent.getIosPermissionDelegate(permissionIos: Permission_iOS): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(permissionIos.name))
    return permissionDelegate
}
