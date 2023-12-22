
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


class AndroidPlatform : Platform {
    override val name: String = "Android"
}

class AndroidScreenSize() : ScreenSize {
    override var screenHeight: Dp = 0.dp
    override var screenWidth: Dp = 0.dp
}

@Composable
actual fun getScreenSize(): ScreenSize {
    val androidScreenSize = remember { AndroidScreenSize() }

    androidScreenSize.screenHeight = LocalConfiguration.current.screenHeightDp.dp
    androidScreenSize.screenWidth = LocalConfiguration.current.screenWidthDp.dp

    return androidScreenSize
}

@Composable
actual fun show(title: String, message: String) {
    val alertState = remember { mutableStateOf(true) }

    if (alertState.value) {
        AlertDialog.Builder(LocalContext.current)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                alertState.value = false
                dialog.dismiss()
            })
            .show()
    }
}

actual class Navigator : NavigatorHandler {
    private lateinit var navController: NavHostController

    actual override fun setNavController(navController: Any) {
        this.navController = navController as NavHostController
    }

    actual override fun navigateTo(screen: String) {
        navController.navigate(screen)
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()