import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.Koin
import permissions.PermissionsContent
import permissions.service.Permissions
import permissions.service.AndroidPermissions
import permissions.service.IosPermissions
import permissions.service.Services

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(koin: Koin) {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            PermissionsContent(
                services = koin.get<Services>(),
                commonPermissions = koin.get<Permissions>(),
                androidPermissions = koin.get<AndroidPermissions>(),
                iosPermissions = koin.get<IosPermissions>()
            )
        }
    }
}