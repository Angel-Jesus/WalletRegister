package angel.panduro.dev.walletregister.presentation.viewmodel

import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetBalanceWalletUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetCreditLineCardUseCase
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect.*
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent
import angel.panduro.dev.walletregister.presentation.contract.home.HomeUiState
import angel.panduro.dev.walletregister.presentation.ui.mapper.toUi
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID

class HomeViewModel(
    private val getCreditLineCardUsedUseCase: GetCreditLineCardUseCase,
    private val getBalanceWalletUseCase: GetBalanceWalletUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase
): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState()) {

    override fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.GetAllCards -> getAllCards()
            is HomeEvent.GetCreditLineUsed -> getCreditLineUsed(event.cardId)
            is HomeEvent.GetDebtResume -> getDebtResume(event.cardId)
        }
    }

    private fun getCreditLineUsed(cardId: Long){
        executeUseCase(
            useCase = getCreditLineCardUsedUseCase,
            params = GetCreditLineCardUseCase.Params(cardId),
            onSucess = {creditLineUsed ->
                updateState {
                    copy(creditLineUsed = creditLineUsed)
                }
            }
        )
    }

    private fun getDebtResume(cardId: Long){

    }

    private fun getAllCards(){
        executeJobUseCase(
            useCase = getAllCardsUseCase,
            onResult = {
                val idCardPeview = uiState.value.idCardSelected
                val idCardSelected = uiState.value.idCardSelected.takeIf { id -> id != Long.EMPTY_ID } ?: (it.firstOrNull()?.id ?: Long.EMPTY_ID)
                updateState {
                    copy(
                        idCardSelected = idCardSelected,
                        cards = it.toUi()
                    )
                }

                if(idCardSelected != Long.EMPTY_ID && idCardPeview != idCardSelected) {
                    sendEffect(GetAllInformationByCard)
                }
            }
        )
    }
}