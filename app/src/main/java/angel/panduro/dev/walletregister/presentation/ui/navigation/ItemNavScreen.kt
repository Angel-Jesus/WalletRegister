package angel.panduro.dev.walletregister.presentation.ui.navigation

import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import kotlinx.serialization.Serializable

sealed class ItemNavScreen {
    // Drawer Screen
    @Serializable
    data object HomeScreen: ItemNavScreen()
    @Serializable
    data object DebtScreen: ItemNavScreen()
    @Serializable
    data object StatisticsScreen: ItemNavScreen()

    // Option Screen
    @Serializable
    data class CardSectionScreen(val cardInformation: String): ItemNavScreen()
    @Serializable
    data class AddDebtScreen(val idCard: Long): ItemNavScreen()
}