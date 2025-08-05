package angel.panduro.dev.walletregister.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.ui.util.fastFirstOrNull
import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.usecases.DeleteCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsFlowUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetBalanceWalletUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetCreditLineCardUsedUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetIdCardByPreferenceUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveIdCardByPreferencesUseCase
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEffect.*
import angel.panduro.dev.walletregister.presentation.contract.home.HomeEvent
import angel.panduro.dev.walletregister.presentation.contract.home.HomeUiState
import angel.panduro.dev.walletregister.presentation.ui.mapper.toUi
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID
import kotlinx.coroutines.Job
import kotlinx.serialization.json.Json

class HomeViewModel(
    private val getCreditLineCardUsedUseCase: GetCreditLineCardUsedUseCase,
    private val getBalanceWalletUseCase: GetBalanceWalletUseCase,
    private val getAllCardsUseCase: GetAllCardsFlowUseCase,
    private val deleteCardUseCase: DeleteCardUseCase,
    private val getIdCardByPreferenceUseCase: GetIdCardByPreferenceUseCase,
    private val saveIdCardByPreferencesUseCase: SaveIdCardByPreferencesUseCase
): BaseViewModel<HomeUiState, HomeEvent, HomeEffect>(HomeUiState()) {

    val cardsState = createDerivedState(
        transform = { CardsState(it.cards, it.idCardSelected) },
        initialValue = CardsState()
    )

    val balanceState = createDerivedState(
        transform = { state ->
            state.cards.fastFirstOrNull { it.id == state.idCardSelected }?.let { card ->
                BalanceState(card, state.creditLineUsed)
            }
        },
        initialValue = null
    )

    val debtsState = createDerivedState(
        transform = { it.totalDebtByType },
        initialValue = emptyMap()
    )

    val modalState = createDerivedState(
        transform = { ModalState(it.showModal, it.temporalIdCard) },
        initialValue = ModalState()
    )
    private var getCardJob: Job? = null

    override fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.GetAllCards -> getIdCardByPreferences()
            is HomeEvent.GetCreditLineUsed -> getCreditLineUsed(event.cardId)
            is HomeEvent.GetDebtResume -> getDebtResume(event.cardId)
            is HomeEvent.ChangeCardSelected -> updateIdCardSelected(event.idCard)
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
            onResult = {
                updateState { copy(showModal = false, temporalIdCard = Long.EMPTY_ID) }
            }
        )
    }

    private fun getCreditLineUsed(cardId: Long){
        if(cardId == Long.EMPTY_ID) return

        executeUseCase(
            useCase = getCreditLineCardUsedUseCase,
            params = GetCreditLineCardUsedUseCase.Params(cardId),
            onResult = {creditLineUsed ->
                updateState {
                    copy(creditLineUsed = creditLineUsed)
                }
            }
        )
    }

    private fun getDebtResume(cardId: Long){
        if(cardId == Long.EMPTY_ID) return

        executeUseCase(
            useCase = getBalanceWalletUseCase,
            params = GetBalanceWalletUseCase.Params(cardId),
            onResult = { debtByCategory ->
                updateState {
                    copy(totalDebtByType = debtByCategory)
                }
            }
        )
    }

    private fun getIdCardByPreferences(){
        executeUseCase(
            useCase = getIdCardByPreferenceUseCase,
            params = Unit,
            onResult = { idCard ->
                getAllCards(idCard)
            }
        )
    }

    private fun getAllCards(idCard: Long) {
        var isInit = true
        getCardJob?.cancel()
        getCardJob = executeJobUseCase(
            useCase = getAllCardsUseCase,
            params = Unit,
            onResult = { cardsInformation ->
                if(isInit){
                    isInit = false
                    val idCardExisted = cardsInformation.any { it.id == idCard }
                    val idCardSelected = idCard.takeIf { idCardExisted } ?: (cardsInformation.firstOrNull()?.id ?: Long.EMPTY_ID)
                    updateState { copy(cards = cardsInformation.toUi()) }
                    updateIdCardSelected(idCardSelected)
                } else{
                    if(cardsInformation.isEmpty()){
                        updateState { copy(cards = emptyList(), idCardSelected = Long.EMPTY_ID, totalDebtByType = emptyMap()) }
                        updateIdCardSelected(Long.EMPTY_ID)
                    } else {
                        updateState { copy(cards = cardsInformation.toUi()) }
                        val idCardExisted = cardsInformation.any { it.id == uiState.value.idCardSelected }
                        if(!idCardExisted) updateIdCardSelected(cardsInformation.first().id)
                    }
                }
            }
        )
    }

    private fun updateIdCardSelected(idCard: Long){
        executeUseCase(
            useCase = saveIdCardByPreferencesUseCase,
            params = SaveIdCardByPreferencesUseCase.Params(idCard),
            onResult = {
                updateState { copy(idCardSelected = idCard) }
                sendEffect(GetAllInformationByCard)
            }
        )
    }

    @Stable
    data class CardsState(
        val cards: List<CardInformation> = emptyList(),
        val idCardSelected: Long = Long.EMPTY_ID
    )

    @Stable
    data class BalanceState(
        val card: CardInformation,
        val creditLineUsed: Float
    )

    @Stable
    data class ModalState(
        val showModal: Boolean = false,
        val temporalIdCard: Long = Long.EMPTY_ID
    )
}