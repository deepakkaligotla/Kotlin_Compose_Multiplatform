
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import navigation.MainNavOption
import navigation.NavRoutes
import navigation.drawer.AppDrawerContent
import navigation.drawer.DrawerParams

@Composable
actual fun MainModalNavDrawer(
    drawerState: DrawerState,
    navigatorHandler: NavigatorHandler
) {
    val navController = rememberNavController()
    navigatorHandler.setNavController(navController)

    ModalNavigationDrawer(
        modifier = Modifier.testTag("drawerNavigation"),
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defaultPick = MainNavOption.HomeScreen
            ) { onUserPickedOption ->
                when (onUserPickedOption) {
                    MainNavOption.HomeScreen -> {
                        navigatorHandler.navigateTo(MainNavOption.HomeScreen.name)
                    }

                    MainNavOption.SettingsScreen -> {
                        navigatorHandler.navigateTo(MainNavOption.SettingsScreen.name)
                    }

                    MainNavOption.AboutScreen -> {
                        navigatorHandler.navigateTo(MainNavOption.AboutScreen.name)
                    }

                    else -> {}
                }
            }
        },
        gesturesEnabled = true
    ) {
        NavHost(navController, startDestination = NavRoutes.MainRoute.name) {
            mainGraph()
        }
    }
}
