package angel.panduro.dev.walletregister.presentation.viewmodel

import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.usecases.DeleteCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetBalanceWalletUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetCreditLineCardUseCase
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect.*
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent
import angel.panduro.dev.walletregister.presentation.contract.home.HomeUiState
import angel.panduro.dev.walletregister.presentation.ui.mapper.toUi
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID
import kotlinx.coroutines.Job
import kotlinx.serialization.json.Json

class HomeViewModel(
    private val getCreditLineCardUsedUseCase: GetCreditLineCardUseCase,
    private val getBalanceWalletUseCase: GetBalanceWalletUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState()) {

    var getCardJob: Job? = null

    override fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.GetAllCards -> getAllCards()
            is HomeEvent.GetCreditLineUsed -> getCreditLineUsed(event.cardId)
            is HomeEvent.GetDebtResume -> getDebtResume(event.cardId)
            is HomeEvent.ChangeCardSelected -> changeCardSelected(event.idCard)
            is HomeEvent.DeleteCard -> deleteCard(event.cardId)
            is HomeEvent.HideModal -> updateState { copy(showModal = false, temporalIdCard = Long.EMPTY_ID) }
            is HomeEvent.ShowModal -> updateState { copy(showModal = true, temporalIdCard = event.temporalIdCard) }
            is HomeEvent.EditCard -> editCard(event.cardId)
        }
    }

    private fun editCard(cardId: Long) {
        updateState { copy(showModal = false, temporalIdCard = Long.EMPTY_ID) }
        val cardChoosed = uiState.value.cards.firstOrNull { it.id == cardId }
        cardChoosed?.let {
            val cardJson = Json.encodeToString(cardChoosed)
            sendEffect(OnSettingCard(cardJson))
        }
    }

    private fun deleteCard(cardId: Long) {
        executeUseCase(
            useCase = deleteCardUseCase,
            params = DeleteCardUseCase.Params(cardId),
            onSucess = {
                updateState { copy(showModal = false, temporalIdCard = Long.EMPTY_ID) }
            }
        )
    }
    private fun changeCardSelected(idCard: Long){
        updateState {
            copy(idCardSelected = idCard)
        }

        sendEffect(GetAllInformationByCard)
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
        executeUseCase(
            useCase = getBalanceWalletUseCase,
            params = GetBalanceWalletUseCase.Params(cardId),
            onSucess = { debtByCategory ->
                updateState {
                    copy(totalDebtByType = debtByCategory)
                }
            }
        )
    }

    private fun getAllCards(){
        getCardJob?.cancel()
        getCardJob = executeJobUseCase(
            useCase = getAllCardsUseCase,
            onResult = { cardInformation ->
                val idCardExits = cardInformation.any { it.id == uiState.value.idCardSelected }
                val idCardSelected = uiState.value.idCardSelected.takeIf { idCardExits } ?: (cardInformation.firstOrNull()?.id ?: Long.EMPTY_ID)
                updateState {
                    copy(
                        idCardSelected = idCardSelected,
                        cards = cardInformation.toUi()
                    )
                }

                if(idCardSelected != Long.EMPTY_ID) {
                    sendEffect(GetAllInformationByCard)
                }
            }
        )
    }
}