package angel.panduro.dev.walletregister.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavigation
import angel.panduro.dev.walletregister.presentation.ui.theme.ContainerDarkColor
import angel.panduro.dev.walletregister.presentation.ui.theme.DrawerItemSelectedColor
import angel.panduro.dev.walletregister.presentation.ui.theme.LabelStyle
import angel.panduro.dev.walletregister.presentation.ui.theme.GreenTopBarColor
import angel.panduro.dev.walletregister.presentation.ui.theme.TitleStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WalletNavigatorDrawer(
    scope: CoroutineScope,
    enableGestures: Boolean = true,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Open),
    onItemClick: (ItemNavScreen) -> Unit = {},
    itemSelected: Int = 0,
    content: @Composable () -> Unit
){
    Surface(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = GreenTopBarColor,
                    drawerShape = RoundedCornerShape(0.dp)
                ){
                    WalletTitle(modifier = Modifier.fillMaxWidth())
                    WalletContent(
                        modifier = Modifier.fillMaxSize(),
                        itemSelected = itemSelected,
                        onItemClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onItemClick(it)
                        }
                    )
                }
            },
            gesturesEnabled = enableGestures,
            drawerState = drawerState,
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletTitle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(GreenTopBarColor),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .height(60.dp)
                    .fillMaxWidth(0.2f),
                painter = painterResource(id = R.drawable.logo_wallet),
                contentDescription = "LogoApp"
            )

            Text(
                text = "Wallet: App de finanzas",
                style = TitleStyle,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletContent(
    modifier: Modifier = Modifier,
    itemSelected: Int = 0,
    onItemClick: (ItemNavScreen) -> Unit = {}
){
    Column(
        modifier = modifier
            .background(ContainerDarkColor)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ItemNavigation.sections.forEachIndexed { index, item ->
            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                label = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = item.title,
                        style = LabelStyle,
                        color = Color.White
                    )
                },
                selected = index == itemSelected,
                onClick = {
                    onItemClick(item.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = if (index == itemSelected) {
                            ImageVector.vectorResource(item.selectedIcon)
                        } else {
                            ImageVector.vectorResource(item.unselectedIcon)
                        },
                        tint = item.color,
                        contentDescription = item.title
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = DrawerItemSelectedColor
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun WalletNavigatorDrawerPreview() {
    WalletNavigatorDrawer(){
        Box {

        }
    }
}*/
