import androidx.compose.ui.window.ComposeUIViewController
import permissions.PermissionsContent
import permissions.service.AndroidPermissions
import permissions.service.IosPermissions
import permissions.service.Permissions
import permissions.service.Services
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val koin = initKoin().koin

    return ComposeUIViewController {
        PermissionsContent(
            services = koin.get<Services>(),
            commonPermissions = koin.get<Permissions>(),
            androidPermissions = koin.get<AndroidPermissions>(),
            iosPermissions = koin.get<IosPermissions>()
        )
    }
}