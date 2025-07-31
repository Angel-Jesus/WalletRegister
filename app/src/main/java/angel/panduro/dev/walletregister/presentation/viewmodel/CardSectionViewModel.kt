package angel.panduro.dev.walletregister.presentation.viewmodel

import android.util.Log
import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.usecases.SaveCreditCardSafeUseCase
import angel.panduro.dev.walletregister.presentation.contract.cardsection.CardSectionEffect
import angel.panduro.dev.walletregister.presentation.contract.cardsection.CardSectionEffect.MissingFields
import angel.panduro.dev.walletregister.presentation.contract.cardsection.CardSectionEffect.SuccessSaveCard
import angel.panduro.dev.walletregister.presentation.contract.cardsection.CardSectionEvent
import angel.panduro.dev.walletregister.presentation.contract.cardsection.CardSectionUiState
import angel.panduro.dev.walletregister.presentation.ui.enums.CardInformationEnum
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import kotlinx.serialization.json.Json

class CardSectionViewModel(
    private val saveCreditCardUseCase: SaveCreditCardSafeUseCase
): BaseViewModel<CardSectionUiState, CardSectionEvent, CardSectionEffect>(CardSectionUiState()) {

    override fun onEvent(event: CardSectionEvent) {
        when(event){
            is CardSectionEvent.ColorChanged -> colorChanged(event.value)
            is CardSectionEvent.InputChanged -> inputChanged(event.type, event.value)
            is CardSectionEvent.SaveCard -> saveCard()
            is CardSectionEvent.InitCardInformation -> initCardInformation(event.cardInformationJson)
        }
    }

    private fun initCardInformation(cardInformationJson: String){
        val cardInformation = Json.decodeFromString<CardInformation>(cardInformationJson)
        updateState {
            copy(
                creditCardName = cardInformation.nameCard,
                creditLineValue = cardInformation.creditLineCard,
                moneyType = cardInformation.typeMoney,
                paymentDueDate = cardInformation.paidDateExpired.toString(),
                closingDate = cardInformation.dateClose.toString(),
                colorCard = cardInformation.colorCard
            )
        }
    }

    private fun inputChanged(type: CardInformationEnum, value: String){
        when(type){
            CardInformationEnum.CREDIT_CARD_NAME -> updateState { copy(creditCardName = value) }
            CardInformationEnum.CREDIT_LINE_VALUE -> updateState { copy(creditLineValue = value) }
            CardInformationEnum.MONEY_TYPE -> updateState { copy(moneyType = value) }
            CardInformationEnum.PAYMENT_DUE_DATE -> updateState { copy(paymentDueDate = value) }
            CardInformationEnum.CLOSING_DATE -> updateState { copy(closingDate = value) }
        }
    }

    private fun colorChanged(value: ULong){
        updateState {
            copy(colorCard = value)
        }
    }

    private fun saveCard(){
        // Check Parameters
        if(uiState.value.validateFields()){
            executeUseCase(
                useCase = saveCreditCardUseCase,
                params = SaveCreditCardSafeUseCase.Params(
                    CardInformationModel(
                        nameCard = uiState.value.creditCardName,
                        creditLineCard = uiState.value.creditLineValue,
                        typeMoney = uiState.value.moneyType,
                        paidDateExpired = uiState.value.paymentDueDate.toInt(),
                        dateClose = uiState.value.closingDate.toInt(),
                        colorCard = uiState.value.colorCard,
                    )
                ),
                onSucess = {
                    Log.d("onSucessSaveCard", "SendEffect SuccessSaveCard")
                    sendEffect(SuccessSaveCard)
                }
            )
        } else{
            sendEffect(MissingFields)
        }
    }

    private fun CardSectionUiState.validateFields(): Boolean {
        if((paymentDueDate.toIntOrNull() ?: 0) < 1) return false
        if((closingDate.toIntOrNull() ?: 0) < 1) return false
        if((creditLineValue.toIntOrNull() ?: 0) < 1) return false
        return creditCardName.isNotBlank() && moneyType.isNotBlank()
    }
}