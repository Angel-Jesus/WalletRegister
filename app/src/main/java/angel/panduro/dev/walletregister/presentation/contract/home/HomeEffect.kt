package angel.panduro.dev.walletregister.presentation.contract.home

import angel.panduro.dev.walletregister.core.base.ui.BaseEffect

sealed class HomeEffect: BaseEffect {
    data object GetAllInformationByCard: HomeEffect()
    data class OnSettingCard(val cardInformationJson: String): HomeEffect()
}