package angel.panduro.dev.walletregister.presentation.contract.home

import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID

data class HomeUiState(
    val cards: List<CardInformation> = emptyList(),
    val idCardSelected: Long = Long.EMPTY_ID,
    val creditLineUsed: Float = 0.0f,
    val totalDebtByType: Map<String, CategoryInformation> = emptyMap(),
    val showModal: Boolean = false,
    val temporalIdCard: Long = Long.EMPTY_ID
): BaseUiState