package angel.panduro.dev.walletregister.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.ui.util.fastFirst
import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetCreditLineAvailableByCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveDebtUseCase
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtEffect
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtEvent
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtUiState
import angel.panduro.dev.walletregister.presentation.ui.mapper.toUi
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.theme.WalletIconColor
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.ZERO
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CategoriesEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtDropDownEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtDropDownEnum.*
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtInputEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtInputEnum.*
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.toSafeFloat

class AddDebtViewModel(
    private val creditLineAvailableUseCase: GetCreditLineAvailableByCardsUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val saveDebtUseCase: SaveDebtUseCase
): BaseViewModel<AddDebtUiState, AddDebtEvent, AddDebtEffect>(AddDebtUiState()) {

    val accountState = createDerivedState(
        transform = { AccountState(it.cardAccounts, it.amountAvailableByCards,it.account, it.amountAvailable, it.accountColor) },
        initialValue = AccountState()
    )

    val amountState = createDerivedState(
        transform = { it.amount },
        initialValue = String.EMPTY
    )

    val quoteState = createDerivedState(
        transform = { it.quote },
        initialValue = String.EMPTY
    )

    val categoryState = createDerivedState(
        transform = { it.category },
        initialValue = CategoriesEnum.FOOD.description
    )


    override fun onEvent(event: AddDebtEvent) {
        when(event){
            is AddDebtEvent.GetAllCards -> getAllCards()
            is AddDebtEvent.UpdateDropDownValue -> updateDropDownValue(event.value, event.type)
            is AddDebtEvent.UpdateInputValue -> updateInputValue(event.value, event.type)
            is AddDebtEvent.SaveDebt -> saveDebt(event.idCard)
        }
    }

    private fun saveDebt(idCard: Long) {
        val checkFields = uiState.value.quote.isNotEmpty() && uiState.value.amount.isNotEmpty() && (uiState.value.amount.toSafeFloat() <= uiState.value.amountAvailable)
        if(checkFields){
            executeUseCase(
                useCase = saveDebtUseCase,
                params = SaveDebtUseCase.Params(
                    idCard = idCard,
                    account = uiState.value.account,
                    category = uiState.value.category,
                    quote = uiState.value.quote,
                    debt = uiState.value.amount
                ),
                onResult = {
                    sendEffect(AddDebtEffect.FinishSaveDebt)
                }
            )
        } else {
            sendEffect(AddDebtEffect.PendingFields)
        }
    }

    private fun getAllCards(){
        executeUseCase(
            useCase = getAllCardsUseCase,
            params = Unit,
            onResult = { cardsList ->
                getAmountAvaibleByCards(cardsList)
            }
        )
    }

    private fun getAmountAvaibleByCards(cardsList: List<CardInformationModel>) {
        executeUseCase(
            useCase = creditLineAvailableUseCase,
            params = GetCreditLineAvailableByCardsUseCase.Params(cardsList),
            onResult = { amountAvailableByCards ->
                updateState {
                    copy(
                        cardAccounts = cardsList.toUi(),
                        amountAvailableByCards = amountAvailableByCards,
                        amountAvailable = amountAvailableByCards.firstOrNull() ?: Float.ZERO,
                        account = cardsList.firstOrNull()?.nameCard ?: String.EMPTY,
                        accountColor = cardsList.firstOrNull()?.colorCard ?: WalletIconColor.value
                    )
                }
            }
        )
    }

    private fun updateDropDownValue(value: String, type: DebtDropDownEnum) {
        when(type){
            ACCOUNT -> {
                val indexCardsListSelected = uiState.value.cardAccounts.indexOfFirst { it.nameCard == value }
                updateState {
                    copy(
                        account = value,
                        accountColor = cardAccounts.fastFirst { it.nameCard == value }.colorCard,
                        amountAvailable = amountAvailableByCards.getOrNull(indexCardsListSelected) ?: Float.ZERO)
                }
            }
            CATEGORY -> updateState { copy(category = value) }
        }
    }

    private fun updateInputValue(value: String, type: DebtInputEnum) {
        when(type){
            AMOUNT -> updateState { copy(amount = value) }
            QUOTE -> updateState { copy(quote = value) }
        }
    }

    @Stable
    data class AccountState(
        val cards: List<CardInformation> = emptyList(),
        val debtByCards: List<Float> = emptyList(),
        val nameCard: String = String.EMPTY,
        val amountAvailable: Float = Float.ZERO,
        val colorCard: ULong = WalletIconColor.value
    )
}