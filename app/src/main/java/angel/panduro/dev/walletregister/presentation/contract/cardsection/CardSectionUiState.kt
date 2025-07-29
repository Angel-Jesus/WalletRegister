package angel.panduro.dev.walletregister.presentation.contract.cardsection

import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.theme.CardWalletList
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY

data class CardSectionUiState(
    val creditCardName: String = String.EMPTY,
    val creditLineValue: String = String.EMPTY,
    val moneyType: String = "PEN",
    val paymentDueDate: String = String.EMPTY,
    val closingDate: String = String.EMPTY,
    val colorCard: ULong = CardWalletList.first().value
): BaseUiState
