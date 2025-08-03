package angel.panduro.dev.walletregister.presentation.ui.utils.enums

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen
import angel.panduro.dev.walletregister.presentation.ui.navigation.ItemNavScreen.*
import angel.panduro.dev.walletregister.presentation.ui.theme.debtIconColor
import angel.panduro.dev.walletregister.presentation.ui.theme.homeIconColor
import angel.panduro.dev.walletregister.presentation.ui.theme.statisticIconColor

enum class DrawerOptionEnum(val title: String, val selectedIcon: Int, val unselectedIcon: Int, val color: Color, val route: ItemNavScreen) {
    HOME(
        title = "Home",
        color = homeIconColor,
        selectedIcon = R.drawable.icon_selected_home,
        unselectedIcon = R.drawable.icon_unselected_home,
        route = HomeScreen
    ),

    DEBT(
        title = "Deudas",
        color = debtIconColor,
        selectedIcon = R.drawable.icon_selected_debt,
        unselectedIcon = R.drawable.icon_unselected_debt,
        route = DebtScreen
    ),
    STATISTIC(
        title = "Estadistica",
        color = statisticIconColor,
        selectedIcon = R.drawable.icon_selected_statistic,
        unselectedIcon = R.drawable.icon_unselected_statistic,
        route = StatisticsScreen
    );

    companion object{
        val sections: List<DrawerOptionEnum> = entries

        @RequiresApi(Build.VERSION_CODES.S)
        fun getSectionForRoute(route: String?): Int {
            if (route == null) return 0
            return when(route){
                HomeScreen::class.qualifiedName, CardSectionScreen::class.qualifiedName -> 0
                DebtScreen::class.qualifiedName, AddDebtScreen::class.qualifiedName  -> 1
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
}