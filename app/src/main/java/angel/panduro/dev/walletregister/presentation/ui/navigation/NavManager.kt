package angel.panduro.dev.walletregister.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import angel.panduro.dev.walletregister.presentation.ui.component.WalletNavigatorDrawer
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavigation.getEnableGesturesForRoute
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavigation.getSectionForRoute
import angel.panduro.dev.walletregister.presentation.ui.screen.cardsection.CardSectionScreen
import angel.panduro.dev.walletregister.presentation.ui.screen.home.HomeScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavManager(){
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    WalletNavigatorDrawer(
        scope = scope,
        enableGestures = getEnableGesturesForRoute(navBackStackEntry?.destination?.route),
        drawerState = drawerState,
        onItemClick = {
            navController.navigate(it)
        },
        itemSelected = getSectionForRoute(navBackStackEntry?.destination?.route),
        content = {
            NavHost(
                navController = navController,
                startDestination = ItemNavScreen.HomeScreen
            ){
                composable<ItemNavScreen.HomeScreen> {
                    HomeScreen(
                        onDisplayDrawer = { scope.launch { drawerState.open() } },
                        onSettingCard = { navController.navigate(ItemNavScreen.CardSectionScreen(it)) }
                    )
                }

                composable<ItemNavScreen.CardSectionScreen> {
                    val arg = it.toRoute<ItemNavScreen.CardSectionScreen>()
                    CardSectionScreen(
                        cardInformation = arg.cardInformation,
                        onBack = { navController.navigate(ItemNavScreen.HomeScreen) }
                    )
                }
            }
        }
    )


}