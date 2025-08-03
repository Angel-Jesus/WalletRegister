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
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DrawerOptionEnum.Companion.getEnableGesturesForRoute
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DrawerOptionEnum.Companion.getSectionForRoute
import angel.panduro.dev.walletregister.presentation.ui.screen.add_debt.AddDebtScreen
import angel.panduro.dev.walletregister.presentation.ui.screen.card_section.CardSectionScreen
import angel.panduro.dev.walletregister.presentation.ui.screen.debt.DebtScreen
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
        onItemClick = {prevScreen, newScreen ->
            navController.navigate(newScreen){
                popUpTo(prevScreen){ inclusive = true }
            }
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
                        onSettingCard = {
                            navController.navigate(ItemNavScreen.CardSectionScreen(it)){
                                popUpTo(ItemNavScreen.HomeScreen) { inclusive = true }
                            }
                        }
                    )
                }

                composable<ItemNavScreen.CardSectionScreen> {
                    val arg = it.toRoute<ItemNavScreen.CardSectionScreen>()
                    CardSectionScreen(
                        cardInformation = arg.cardInformation,
                        onBack = { navController.navigate(ItemNavScreen.HomeScreen) }
                    )
                }

                composable<ItemNavScreen.DebtScreen> {
                    DebtScreen(
                        onDisplayDrawer = { scope.launch { drawerState.open() } },
                        onAddDebt = { idCard ->
                            navController.navigate(ItemNavScreen.AddDebtScreen(idCard)){
                                popUpTo(ItemNavScreen.DebtScreen) { inclusive = true }
                            }
                        }
                    )
                }

                composable<ItemNavScreen.AddDebtScreen> {
                    val arg = it.toRoute<ItemNavScreen.AddDebtScreen>()
                    AddDebtScreen(
                        idCard = arg.idCard,
                        onBack = { navController.navigate(ItemNavScreen.DebtScreen) }
                    )
                }
            }
        }
    )


}