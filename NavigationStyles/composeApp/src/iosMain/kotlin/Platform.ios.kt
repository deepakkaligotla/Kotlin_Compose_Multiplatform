
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import navigation.MainNavOption
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIDevice
import platform.UIKit.UINavigationController
import platform.UIKit.UIScreen

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName()
}

class IOSScreenSize() : ScreenSize {
    override var screenHeight: Dp = 0.dp
    override var screenWidth: Dp = 0.dp
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getScreenSize(): ScreenSize {
    val iosScreenSize = remember { IOSScreenSize() }

    iosScreenSize.screenHeight = CGRectGetHeight(UIScreen.mainScreen.bounds).dp
    iosScreenSize.screenWidth = CGRectGetWidth(UIScreen.mainScreen.bounds).dp

    return iosScreenSize
}

actual class Navigator : NavigatorHandler {
    private lateinit var navController: UINavigationController

    actual override fun setNavController(navController: Any) {
        this.navController = navController as UINavigationController
    }

    actual override fun navigateTo(screen: String) {
        println(screen)
        when(screen) {
            MainNavOption.HomeScreen.name -> navController.pushViewController(homeViewController(), true)
            MainNavOption.SettingsScreen.name -> navController.pushViewController(settingsViewController(), true)
            MainNavOption.AboutScreen.name -> navController.pushViewController(aboutViewController(), true)
        }
    }
}

@Composable
actual fun show(title: String, message: String) {
    val alertState = remember { mutableStateOf(true) }

    if (alertState.value) {
        AlertDialog(
            onDismissRequest = {
                alertState.value = false
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Button(
                    onClick = {
                        alertState.value = false
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}