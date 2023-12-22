package screen.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun MainBottomBar() {
    BottomAppBar(
        contentPadding = PaddingValues()
    ) {
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home")
        }

        IconButton(
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile"
            )
        }

        IconButton(
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "About"
            )
        }
    }
}