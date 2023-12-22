package navigation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import navigation.MainNavOption

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            MainNavOption.HomeScreen,
            "Home",
            Icons.Default.Home,
            "Home Screen"
        ),
        AppDrawerItemInfo(
            MainNavOption.SettingsScreen,
            "Settings",
            Icons.Default.Settings,
            "Settings Screen"
        ),
        AppDrawerItemInfo(
            MainNavOption.AboutScreen,
            "About",
            Icons.Default.Info,
            "About Screen"
        )
    )
}