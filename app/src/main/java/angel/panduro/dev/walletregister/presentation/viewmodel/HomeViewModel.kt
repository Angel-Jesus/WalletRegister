package angel.panduro.dev.walletregister.presentation.viewmodel

import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent
import angel.panduro.dev.walletregister.presentation.contract.home.HomeUiState

class HomeViewModel(

): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState()) {

    override fun onEvent(event: HomeEvent) {
        when(event){
            HomeEvent.GetAllCards -> TODO()
            is HomeEvent.GetCreditLineUsed -> TODO()
            is HomeEvent.GetDebtResume -> TODO()
        }
    }
}