package angel.panduro.dev.walletregister.presentation.contract.debt

import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.model.DebtInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID

data class DebtUiState(
    val idCardSelected: Long = Long.EMPTY_ID,
    val debtNotPaid: List<DebtInformation> = emptyList(),
    val debtPaid: List<DebtInformation> = emptyList(),
    val totalDebtByType: Map<String, CategoryInformation> = emptyMap(),
    val showOptionModal: Boolean = false,
    val temporalDebt: Pair<Boolean, Long> = Pair(false, Long.EMPTY_ID)
): BaseUiState
