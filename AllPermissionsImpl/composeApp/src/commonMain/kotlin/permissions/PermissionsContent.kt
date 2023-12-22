package permissions

import Platform
import PlatformModule
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import getPlatform
import kotlinx.coroutines.launch
import permissions.model.Permission
import permissions.model.PermissionState
import permissions.model.Service
import permissions.model.Permission_Android
import permissions.model.Permission_iOS
import permissions.service.AndroidPermissions
import permissions.service.Permissions
import permissions.service.Services
import permissions.service.IosPermissions

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PermissionsContent(
    services: Services,
    commonPermissions: Permissions,
    androidPermissions: AndroidPermissions,
    iosPermissions: IosPermissions
) {
    val scope = rememberCoroutineScope()
    var density by remember { mutableStateOf(1f) }
    val platform: Platform = getPlatform()

    MaterialTheme(
        colors = if(isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement= Arrangement.Center,
                horizontalAlignment= Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Services",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
                Divider(modifier = Modifier.padding(top= 16.dp, bottom = 16.dp))

                Service.entries.toTypedArray().forEach {service ->
                    val permissionState by services.checkServiceFlow(service)
                        .collectAsState(services.checkService(service))
                    PermissionItem(
                        permissionName = service.name,
                        permissionState = permissionState,
                        onRequestClick = {
                            scope.launch {
                                services.provideService(service)
                            }
                        },
                        onOpenSettingsClick = {
                            services.openSettingPage(service)
                        },
                    )
                }
                Divider(modifier = Modifier.padding(top= 16.dp, bottom = 16.dp))

                Text(
                    text = "Permissions",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
                Divider(modifier = Modifier.padding(top= 16.dp, bottom = 16.dp))

                Permission.entries.toTypedArray().forEach { permission ->
                    val permissionState by commonPermissions.checkPermissionFlow(permission)
                        .collectAsState(commonPermissions.checkPermission(permission))
                    PermissionItem(
                        permissionName = permission.name,
                        permissionState = permissionState,
                        onRequestClick = {
                            scope.launch {
                                commonPermissions.providePermission(permission)
                            }
                        },
                        onOpenSettingsClick = {
                            commonPermissions.openSettingPage(permission)
                        },
                    )
                }
                Divider(modifier = Modifier.padding(top= 16.dp, bottom = 16.dp))

                Text(
                    text = "Specific ${platform.name} Permissions",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
                Divider(modifier = Modifier.padding(top= 16.dp, bottom = 16.dp))

                if(platform.name == "Android") {
                    Permission_Android.entries.toTypedArray().forEach { permission ->
                        val permissionState by androidPermissions.checkAndroidPermissionFlow(permission)
                            .collectAsState(androidPermissions.checkAndroidPermission(permission))
                        PermissionItem(
                            permissionName = permission.name,
                            permissionState = permissionState,
                            onRequestClick = {
                                scope.launch {
                                    androidPermissions.provideAndroidPermission(permission)
                                }
                            },
                            onOpenSettingsClick = {
                                androidPermissions.openSettingPage(permission)
                            },
                        )
                    }
                } else if(platform.name == "iOS") {
                    Permission_iOS.entries.toTypedArray().forEach { permission ->
                        val permissionState by iosPermissions.checkIosPermissionFlow(permission)
                            .collectAsState(iosPermissions.checkIosPermission(permission))
                        PermissionItem(
                            permissionName = permission.name,
                            permissionState = permissionState,
                            onRequestClick = {
                                scope.launch {
                                    iosPermissions.provideIosPermission(permission)
                                }
                            },
                            onOpenSettingsClick = {
                                iosPermissions.openSettingPage(permission)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Suppress("FunctionName")
@Composable
internal fun PermissionItem(
    permissionName: String,
    permissionState: PermissionState,
    onRequestClick: () -> Unit,
    onOpenSettingsClick: () -> Unit,
) {
    val colorState by animateColorAsState(
        when (permissionState) {
            PermissionState.GRANTED -> Color.Green
            PermissionState.NOT_DETERMINED -> Color.Gray
            PermissionState.DENIED -> Color.Red
        }, label = ""
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = permissionName,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = when (permissionState) {
                    PermissionState.GRANTED -> Icons.Default.Check
                    PermissionState.NOT_DETERMINED -> Icons.Outlined.QuestionMark
                    PermissionState.DENIED -> Icons.Outlined.Close
                },
                tint = colorState,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Button(
                onClick = onOpenSettingsClick,
            ) {
                Text(
                    text = "Settings",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
        AnimatedVisibility(permissionState.notGranted()) {
            Button(
                onClick = onRequestClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Request",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}