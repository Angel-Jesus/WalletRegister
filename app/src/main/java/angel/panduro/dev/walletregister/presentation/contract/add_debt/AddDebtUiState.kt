package angel.panduro.dev.walletregister.presentation.contract.add_debt

import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.WalletIconColor
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CategoriesEnum

data class AddDebtUiState(
    val cardAccounts: List<CardInformation> = emptyList(),
    val account: String = String.EMPTY,
    val accountColor: Color = WalletIconColor,
    val amount: String = String.EMPTY,
    val quote: String = String.EMPTY,
    val category: String = CategoriesEnum.FOOD.description
): BaseUiState
