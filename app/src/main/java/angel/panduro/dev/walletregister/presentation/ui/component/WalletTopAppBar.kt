package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletTopAppBar(
    titleTopBar: String,
    onDisplayDrawer: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = titleTopBar,
                style = TitleStyle
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onDisplayDrawer
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "DrawerOpenIcon"
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = GreenTopBarColor,
            scrolledContainerColor = GreenTopBarColor,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = GreenTopBarColor
        )
    )
}