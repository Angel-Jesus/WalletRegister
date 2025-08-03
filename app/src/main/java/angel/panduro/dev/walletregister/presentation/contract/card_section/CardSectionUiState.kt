package angel.panduro.dev.walletregister.presentation.contract.card_section

import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.theme.CardWalletList
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CurrencyEnum

data class CardSectionUiState(
    val creditCardName: String = String.EMPTY,
    val creditLineValue: String = String.EMPTY,
    val moneyType: String = CurrencyEnum.SOL_PERUANO.code,
    val paymentDueDay: String = String.EMPTY,
    val closingDay: String = String.EMPTY,
    val colorCard: ULong = CardWalletList.first().value
): BaseUiState
