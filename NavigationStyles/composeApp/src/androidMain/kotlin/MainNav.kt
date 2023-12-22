import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import navigation.MainNavOption
import navigation.NavRoutes
import screen.AboutScreen
import screen.HomeScreen
import screen.SettingsScreen

fun NavGraphBuilder.mainGraph() {
    navigation(startDestination = MainNavOption.HomeScreen.name, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.HomeScreen.name) {
            HomeScreen()
        }

        composable(MainNavOption.SettingsScreen.name) {
            SettingsScreen()
        }

        composable(MainNavOption.AboutScreen.name) {
            AboutScreen()
        }
    }
}