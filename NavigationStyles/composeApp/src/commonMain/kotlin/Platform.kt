import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface Platform {
    val name: String
}

interface ScreenSize {
    val screenHeight: Dp
    val screenWidth: Dp
}

interface NavigatorHandler {
    fun setNavController(navController: Any)
    fun navigateTo(screen: String)
}

expect fun getPlatform(): Platform

@Composable
expect fun getScreenSize(): ScreenSize

expect class Navigator(): NavigatorHandler {
    override fun setNavController(navController: Any)
    override fun navigateTo(screen: String)
}

@Composable
expect fun show(title: String, message: String)

@Composable
expect fun MainModalNavDrawer(
    drawerState: DrawerState,
    navigatorHandler: NavigatorHandler
)