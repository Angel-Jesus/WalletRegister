package angel.panduro.dev.walletregister.presentation.contract.add_debt

import angel.panduro.dev.walletregister.core.base.ui.BaseEffect

sealed class AddDebtEffect: BaseEffect {
    data object PendingFields: AddDebtEffect()
    data object FinishSaveDebt: AddDebtEffect()
}