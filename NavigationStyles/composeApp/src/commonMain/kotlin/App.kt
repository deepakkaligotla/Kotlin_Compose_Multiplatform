
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import screen.common.MainBottomBar
import screen.common.MainTopBar

@Composable
fun App(navigatorHandler: NavigatorHandler) {
    val drawerState by remember { mutableStateOf(DrawerState(DrawerValue.Closed)) }
    var userTheme by remember { mutableStateOf( true) }
    val screenSize = getScreenSize()

    MaterialTheme(
        colorScheme = if(userTheme) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold(
            modifier = Modifier.testTag("MainCompose").fillMaxSize(),
            topBar = { MainTopBar(drawerState) },
            bottomBar = { MainBottomBar() }
        ) {paddingValues ->
            Surface(
                modifier = Modifier
                    .height(screenSize.screenHeight)
                    .width(screenSize.screenWidth)
                    .padding(paddingValues)
            ) {
                BoxWithConstraints(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center,
                    propagateMinConstraints = true,
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        MainModalNavDrawer(drawerState, navigatorHandler)
                    }
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        show("Screen Size", "Height: "+screenSize.screenHeight+", Width: "+screenSize.screenWidth)
                    }
                    Box(
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconButton(
                            onClick = {
                                userTheme = !userTheme
                            }
                        ) {
                            Icon(
                                imageVector = if(userTheme)
                                    Icons.Default.LightMode
                                else
                                    Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.clip(MaterialTheme.shapes.extraLarge)
                            )
                        }
                    }
                }
            }
        }
    }
}
