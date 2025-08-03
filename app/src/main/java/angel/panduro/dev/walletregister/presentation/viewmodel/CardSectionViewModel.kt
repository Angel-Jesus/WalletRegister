package angel.panduro.dev.walletregister.presentation.viewmodel

import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.usecases.SaveCreditCardSafeUseCase
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEffect
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEffect.MissingFields
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEffect.SuccessSaveCard
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionEvent
import angel.panduro.dev.walletregister.presentation.contract.card_section.CardSectionUiState
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.enums.CardInformationEnum
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.toSafeInt
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
                paymentDueDay = cardInformation.paidDateExpired.toString(),
                closingDay = cardInformation.dateClose.toString(),
                colorCard = cardInformation.colorCard
            )
        }
    }

    private fun inputChanged(type: CardInformationEnum, value: String){
        when(type){
            CardInformationEnum.CREDIT_CARD_NAME -> updateState { copy(creditCardName = value) }
            CardInformationEnum.CREDIT_LINE_VALUE -> updateState { copy(creditLineValue = value) }
            CardInformationEnum.MONEY_TYPE -> updateState { copy(moneyType = value) }
            CardInformationEnum.PAYMENT_DUE_DATE -> updateState { copy(paymentDueDay = value) }
            CardInformationEnum.CLOSING_DATE -> updateState { copy(closingDay = value) }
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
                        paidDateExpired = uiState.value.paymentDueDay.toSafeInt(),
                        dateClose = uiState.value.closingDay.toSafeInt(),
                        colorCard = uiState.value.colorCard,
                    )
                ),
                onResult = {
                    sendEffect(SuccessSaveCard)
                }
            )
        } else{
            sendEffect(MissingFields)
        }
    }

    private fun CardSectionUiState.validateFields(): Boolean {
        if(paymentDueDay.toSafeInt() < 1) return false
        if(closingDay.toSafeInt() < 1) return false
        if(creditLineValue.toSafeInt() < 1) return false
        return creditCardName.isNotBlank() && moneyType.isNotBlank()
    }
}