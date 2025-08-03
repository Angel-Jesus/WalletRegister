package angel.panduro.dev.walletregister.presentation.contract.debt

import angel.panduro.dev.walletregister.core.base.ui.BaseEvent

sealed class DebtEvent: BaseEvent {
    data object GetDebtAllInformation: DebtEvent()
    data object PaidOneQuoteDebt: DebtEvent()
    data object DeleteDebt: DebtEvent()
    data class ShowQuestionModal(val idDebt: Long, val isPaid: Boolean): DebtEvent()
    data object HideQuestionModal: DebtEvent()
}