package navigation.drawer

import androidx.compose.ui.graphics.vector.ImageVector

data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    val title: String,
    val icon: ImageVector,
    val descriptionId: String
)