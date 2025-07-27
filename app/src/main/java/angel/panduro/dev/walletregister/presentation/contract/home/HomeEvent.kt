package angel.panduro.dev.walletregister.presentation.contract.home

import angel.panduro.dev.walletregister.core.base.ui.BaseEvent

sealed class HomeEvent: BaseEvent {
    data object GetAllCards: HomeEvent()
    data class GetCreditLineUsed(val cardId: Long): HomeEvent()
    data class GetDebtResume(val cardId: Long): HomeEvent()
}