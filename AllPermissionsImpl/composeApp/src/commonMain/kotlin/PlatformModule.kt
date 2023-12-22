import org.koin.core.module.Module
import org.koin.dsl.module
import permissions.service.AndroidPermissions
import permissions.service.AndroidPermissionsImpl
import permissions.service.IosPermissions
import permissions.service.IosPermissionsImpl
import permissions.service.Permissions
import permissions.service.PermissionsImpl
import permissions.service.Services
import permissions.service.ServicesImpl

interface PlatformModule {
    val permissionsModule: Module
        get() = module {
            includes(platformModule())

            single<Services> {
                ServicesImpl()
            }
            single<Permissions> {
                PermissionsImpl()
            }
            single<AndroidPermissions> {
                AndroidPermissionsImpl()
            }
            single<IosPermissions> {
                IosPermissionsImpl()
            }
        }
}

internal expect fun platformModule(): Module

val permissionsModule: Module = module {
    includes(platformModule())

    single<Services> {
        ServicesImpl()
    }
    single<Permissions> {
        PermissionsImpl()
    }
    single<AndroidPermissions> {
        AndroidPermissionsImpl()
    }
    single<IosPermissions> {
        IosPermissionsImpl()
    }
}

interface Platform {
    val name: String
    val isSystemInDarkTheme: Boolean
}

expect fun getPlatform(): Platform