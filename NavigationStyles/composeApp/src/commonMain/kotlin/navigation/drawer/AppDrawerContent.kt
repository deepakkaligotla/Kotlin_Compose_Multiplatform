package navigation.drawer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import getScreenSize
import kotlinx.coroutines.launch

@Composable
fun <T : Enum<T>> AppDrawerContent(
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo<T>>,
    defaultPick: T,
    onClick: (T) -> Unit,
) {
    var currentPick by rememberSaveable { mutableStateOf(defaultPick) }
    val coroutineScope = rememberCoroutineScope()
    val screenSize = getScreenSize()

    ModalDrawerSheet(
        modifier = Modifier
            .width(screenSize.screenWidth.div(2.5.dp).dp)
            .border(1.dp, Color.White)
            .padding(all = 0.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .testTag("drawerNavigationButton"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(menuItems) { item ->
                AppDrawerItem(item = item) { navOption ->

                    if (currentPick == navOption) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        return@AppDrawerItem
                    }

                    currentPick = navOption
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onClick(navOption)
                }
            }
        }
    }
}