package angel.panduro.dev.walletregister.presentation.contract.add_debt

import angel.panduro.dev.walletregister.core.base.ui.BaseEvent
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtDropDownEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtInputEnum

sealed class AddDebtEvent: BaseEvent {
    data object GetAllCards: AddDebtEvent()
    data class SaveDebt(val idCard: Long): AddDebtEvent()
    data class UpdateInputValue(val value: String, val type: DebtInputEnum): AddDebtEvent()
    data class UpdateDropDownValue(val value: String, val type: DebtDropDownEnum): AddDebtEvent()
}