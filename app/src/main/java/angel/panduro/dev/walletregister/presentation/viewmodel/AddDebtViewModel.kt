package angel.panduro.dev.walletregister.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveDebtUseCase
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtEffect
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtEvent
import angel.panduro.dev.walletregister.presentation.contract.add_debt.AddDebtUiState
import angel.panduro.dev.walletregister.presentation.ui.mapper.toUi
import angel.panduro.dev.walletregister.presentation.ui.theme.WalletIconColor
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtDropDownEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtDropDownEnum.*
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtInputEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.DebtInputEnum.*

class AddDebtViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val saveDebtUseCase: SaveDebtUseCase
): BaseViewModel<AddDebtUiState, AddDebtEvent, AddDebtEffect>(AddDebtUiState()) {

    override fun onEvent(event: AddDebtEvent) {
        when(event){
            is AddDebtEvent.GetAllCards -> getAllCards()
            is AddDebtEvent.UpdateDropDownValue -> updateDropDownValue(event.value, event.type)
            is AddDebtEvent.UpdateInputValue -> updateInputValue(event.value, event.type)
            is AddDebtEvent.SaveDebt -> saveDebt(event.idCard)
        }
    }

    private fun saveDebt(idCard: Long) {
        if(uiState.value.quote.isNotEmpty() && uiState.value.amount.isNotEmpty()){
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
                updateState {
                    copy(
                        cardAccounts = cardsList.toUi(),
                        account = cardsList.firstOrNull()?.nameCard ?: String.EMPTY,
                        accountColor = Color(cardsList.firstOrNull()?.colorCard ?: WalletIconColor.value)
                    )
                }
            }
        )
    }

    private fun updateDropDownValue(value: String, type: DebtDropDownEnum) {
        when(type){
            ACCOUNT -> updateState { copy(account = value, accountColor = Color(cardAccounts.first { it.nameCard == account }.colorCard)) }
            CATEGORY -> updateState { copy(category = value) }
        }
    }

    private fun updateInputValue(value: String, type: DebtInputEnum) {
        when(type){
            AMOUNT -> updateState { copy(amount = value) }
            QUOTE -> updateState { copy(quote = value) }
        }
    }
}