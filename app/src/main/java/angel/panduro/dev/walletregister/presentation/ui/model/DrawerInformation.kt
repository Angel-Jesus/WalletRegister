package angel.panduro.dev.walletregister.presentation.ui.model

import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen

data class DrawerInformation(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val color: Color,
    val route: ItemNavScreen
)
