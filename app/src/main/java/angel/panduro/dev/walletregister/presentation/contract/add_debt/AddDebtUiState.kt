package angel.panduro.dev.walletregister.presentation.contract.add_debt

import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.WalletIconColor
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.ZERO
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CategoriesEnum

data class AddDebtUiState(
    val cardAccounts: List<CardInformation> = emptyList(),
    val amountAvailableByCards: List<Float> = emptyList(),
    val account: String = String.EMPTY,
    val accountColor: ULong = WalletIconColor.value,
    val amount: String = String.EMPTY,
    val amountAvailable: Float = Float.ZERO,
    val quote: String = String.EMPTY,
    val category: String = CategoriesEnum.FOOD.description
): BaseUiState
