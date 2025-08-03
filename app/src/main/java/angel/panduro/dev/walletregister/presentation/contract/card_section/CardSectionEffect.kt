package angel.panduro.dev.walletregister.presentation.contract.card_section

import angel.panduro.dev.walletregister.core.base.ui.BaseEffect

sealed class CardSectionEffect: BaseEffect {
    data object SuccessSaveCard: CardSectionEffect()
    data object MissingFields: CardSectionEffect()
}