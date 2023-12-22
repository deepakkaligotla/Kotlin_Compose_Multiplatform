
import androidx.compose.ui.window.ComposeUIViewController
import screen.AboutScreen
import screen.HomeScreen
import screen.SettingsScreen

val navigatorHandler: NavigatorHandler = Navigator()

fun MainViewController() = ComposeUIViewController { App(navigatorHandler) }

fun homeViewController() = ComposeUIViewController { HomeScreen() }

fun settingsViewController() = ComposeUIViewController { SettingsScreen() }

fun aboutViewController() = ComposeUIViewController { AboutScreen() }