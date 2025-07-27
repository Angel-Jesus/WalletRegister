package angel.panduro.dev.walletregister.presentation.contract.home

import angel.panduro.dev.walletregister.core.base.ui.BaseUiState
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation

data class HomeUiState(
    val cards: List<CardInformation> = emptyList(),
    val idCardSelected: Long = 0,
    val creditLineUsed: Float = 0.0f,
    val totalDebtByType: Map<String, CategoryInformation> = emptyMap()
): BaseUiState