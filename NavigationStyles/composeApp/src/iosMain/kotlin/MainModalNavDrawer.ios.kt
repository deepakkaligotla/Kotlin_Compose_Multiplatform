
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import navigation.MainNavOption
import navigation.drawer.AppDrawerContent
import navigation.drawer.DrawerParams
import platform.UIKit.UINavigationController
import screen.AboutScreen
import screen.HomeScreen
import screen.SettingsScreen

@Composable
actual fun MainModalNavDrawer(
    drawerState: DrawerState,
    navigatorHandler: NavigatorHandler
) {
    val navController = remember { UINavigationController() }
    var open by remember { mutableStateOf(MainNavOption.HomeScreen) }
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
                open = onUserPickedOption
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
        gesturesEnabled = true,
    ) {
        when(open) {
            MainNavOption.HomeScreen -> HomeScreen()
            MainNavOption.SettingsScreen -> SettingsScreen()
            MainNavOption.AboutScreen -> AboutScreen()
        }
    }
}