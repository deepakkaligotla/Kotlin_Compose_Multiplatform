package screen.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MainTopBar(drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    var isTopBarTitleCollapsed by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        isTopBarTitleCollapsed = true
        delay(2000)
        isTopBarTitleCollapsed = false
    }

    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.padding(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { isTopBarTitleCollapsed = !isTopBarTitleCollapsed }
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource("compose-multiplatform.xml"),
                        contentDescription = null
                    )
                }
                AnimatedVisibility(
                    visible = !isTopBarTitleCollapsed
                ) {
                    Text(
                        text = "Deepak Kaligotla",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    if(drawerState.isClosed) {
                        drawerState.open()
                    } else drawerState.close()
                }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        },
        windowInsets = WindowInsets(1.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
            TopAppBarState(0f, 0f, 0f)
        ) {
            false
        }
    )
}