package angel.panduro.dev.walletregister.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.navigation.DescriptionMenu.DEBT
import angel.panduro.dev.walletregister.presentation.ui.navigation.DescriptionMenu.HOME
import angel.panduro.dev.walletregister.presentation.ui.navigation.DescriptionMenu.STATISTICS
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen.AddCardScreen
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen.DebtScreen
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen.HomeScreen
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen.StatisticsScreen
import angel.panduro.dev.walletregister.presentation.ui.theme.debtIconColor
import angel.panduro.dev.walletregister.presentation.ui.theme.homeIconColor
import angel.panduro.dev.walletregister.presentation.ui.theme.statisticIconColor

object ItemNavigation {
    val sections = listOf(
        NavigationItem(
            title = HOME.description,
            color = homeIconColor,
            selectedIcon = R.drawable.icon_selected_home,
            unselectedIcon = R.drawable.icon_unselected_home,
            route = HomeScreen
        ),
        NavigationItem(
            title = DEBT.description,
            color = debtIconColor,
            selectedIcon = R.drawable.icon_selected_debt,
            unselectedIcon = R.drawable.icon_unselected_debt,
            route = DebtScreen
        ),
        NavigationItem(
            title = STATISTICS.description,
            color = statisticIconColor,
            selectedIcon = R.drawable.icon_selected_statistic,
            unselectedIcon = R.drawable.icon_unselected_statistic,
            route = StatisticsScreen
        ),
    )

    @RequiresApi(Build.VERSION_CODES.S)
    fun getSectionForRoute(route: String?): Int {
        if (route == null) return 0
        return when(route){
            HomeScreen::class.qualifiedName, AddCardScreen::class.qualifiedName -> 0
            DebtScreen::class.qualifiedName  -> 1
            StatisticsScreen::class.qualifiedName  -> 2
            else -> 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun getEnableGesturesForRoute(route: String?): Boolean {
        if (route == null) return true
        return when(route){
            HomeScreen::class.qualifiedName -> true
            DebtScreen::class.qualifiedName  -> true
            StatisticsScreen::class.qualifiedName  -> true
            else -> false
        }
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val color: Color,
    val route: ItemNavScreen
)

enum class DescriptionMenu(val description: String) {
    HOME("Home"),
    DEBT("Deudas"),
    STATISTICS("Estadistica")
}