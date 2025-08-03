package angel.panduro.dev.walletregister.presentation.contract.card_section

import angel.panduro.dev.walletregister.core.base.ui.BaseEvent
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CardInformationEnum

sealed class CardSectionEvent: BaseEvent {
    data class InitCardInformation(val cardInformationJson: String): CardSectionEvent()
    data class InputChanged(val type: CardInformationEnum, val value: String): CardSectionEvent()
    data class ColorChanged(val value: ULong): CardSectionEvent()
    data object SaveCard: CardSectionEvent()
}