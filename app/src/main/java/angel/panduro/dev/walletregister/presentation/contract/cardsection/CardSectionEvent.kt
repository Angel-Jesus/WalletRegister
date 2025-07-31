package angel.panduro.dev.walletregister.presentation.contract.cardsection

import angel.panduro.dev.walletregister.core.base.ui.BaseEvent
import angel.panduro.dev.walletregister.presentation.ui.enums.CardInformationEnum

sealed class CardSectionEvent: BaseEvent {
    data class InitCardInformation(val cardInformationJson: String): CardSectionEvent()
    data class InputChanged(val type: CardInformationEnum, val value: String): CardSectionEvent()
    data class ColorChanged(val value: ULong): CardSectionEvent()
    data object SaveCard: CardSectionEvent()
}