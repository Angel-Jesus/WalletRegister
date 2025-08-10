package angel.panduro.dev.walletregister.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import angel.panduro.dev.walletregister.core.base.ui.BaseViewModel
import angel.panduro.dev.walletregister.core.base.ui.EmptyEffect
import angel.panduro.dev.walletregister.domain.model.DebtInformationModel
import angel.panduro.dev.walletregister.domain.usecases.DeleteDebtUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllDebtByIdCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetBalanceWalletUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetIdCardByPreferenceUseCase
import angel.panduro.dev.walletregister.domain.usecases.NotificationSectionUseCase
import angel.panduro.dev.walletregister.domain.usecases.UpdateDebtQuoteUseCase
import angel.panduro.dev.walletregister.presentation.contract.debt.DebtEvent
import angel.panduro.dev.walletregister.presentation.contract.debt.DebtUiState
import angel.panduro.dev.walletregister.presentation.ui.mapper.toUi
import angel.panduro.dev.walletregister.presentation.ui.model.CategoryInformation
import angel.panduro.dev.walletregister.presentation.ui.model.DebtInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_ID
import kotlinx.coroutines.Job

class DebtViewModel(
    private val getIdCardByPreferenceUseCase: GetIdCardByPreferenceUseCase,
    private val getBalanceWalletUseCase: GetBalanceWalletUseCase,
    private val getAllDebtByIdCardUseCase: GetAllDebtByIdCardUseCase,
    private val deleteDebtUseCase: DeleteDebtUseCase,
    private val paidOneQuoteDebtUseCase: UpdateDebtQuoteUseCase,
    private val notificationSectionUseCase: NotificationSectionUseCase
): BaseViewModel<DebtUiState, DebtEvent, EmptyEffect>(DebtUiState()) {

    val cardState = createDerivedState(
        transform = { CardState(it.idCardSelected) },
        initialValue = CardState()
    )

    val debtsState = createDerivedState(
        transform = { DebtsState(it.totalDebtByType, it.debtNotPaid, it.debtPaid) },
        initialValue = DebtsState()
    )

    val modalState = createDerivedState(
        transform = { ModalState(it.showOptionModal, it.temporalDebt) },
        initialValue = ModalState()
    )

    private var getDebtsJob: Job? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEvent(event: DebtEvent) {
        when(event){
            is DebtEvent.GetDebtAllInformation -> getDebtAllInformation()
            is DebtEvent.ShowQuestionModal -> showQuestionModal(event.idDebt, event.isPaid)
            is DebtEvent.HideQuestionModal -> hideQuestionModal()
            is DebtEvent.DeleteDebt -> deleteDebt()
            is DebtEvent.PaidOneQuoteDebt -> paidOneQuoteDebt()
        }
    }

    private fun deleteDebt(){
        executeUseCase(
            useCase = deleteDebtUseCase,
            params = DeleteDebtUseCase.Params(uiState.value.temporalDebt.second),
            onResult = { hideQuestionModal() }
        )
    }

    private fun paidOneQuoteDebt(){
        val debtInformation = uiState.value.debtNotPaid.firstOrNull{ it.id == uiState.value.temporalDebt.second }
        debtInformation?.let {
            executeUseCase(
                useCase = paidOneQuoteDebtUseCase,
                params = UpdateDebtQuoteUseCase.Params(
                    idDebt = uiState.value.temporalDebt.second,
                    quote = debtInformation.quote,
                    quotePaid = debtInformation.quotePaid + 1
                ),
                onResult = { hideQuestionModal() }
            )
        }
    }

    private fun showQuestionModal(idDebt: Long, isPaid: Boolean){
        updateState {
            copy(showOptionModal = true, temporalDebt = Pair(isPaid, idDebt))
        }
    }

    private fun hideQuestionModal(){
        updateState {
            copy(showOptionModal = false, temporalDebt = Pair(false, Long.EMPTY_ID))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDebtAllInformation() {
        executeUseCase(
            useCase = getIdCardByPreferenceUseCase,
            params = Unit,
            onResult = { idCard ->
                updateState { copy(idCardSelected = idCard) }
                getAllDebtByCard(idCard)
                getBalanceByCard(idCard)
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllDebtByCard(idCard: Long){
        getDebtsJob?.cancel()
        getDebtsJob = executeJobUseCase(
            useCase = getAllDebtByIdCardUseCase,
            params = GetAllDebtByIdCardUseCase.Params(idCard),
            onResult = { debtInformation ->
                notificationSection(debtInformation)

                val debtNotPaid = debtInformation.filter {data -> data.isPaid == 0 }
                val debtPaid = debtInformation.filter { data -> data.isPaid == 1 }
                updateState {
                    copy(
                        debtNotPaid = debtNotPaid.toUi(),
                        debtPaid = debtPaid.toUi()
                    )
                }
            }
        )
    }

    private fun notificationSection(debts: List<DebtInformationModel>){
        executeUseCase(
            useCase = notificationSectionUseCase,
            params = NotificationSectionUseCase.Params(cardState.value.idCardSelected, debts)
        )
    }

    private fun getBalanceByCard(idCard: Long){
        executeUseCase(
            useCase = getBalanceWalletUseCase,
            params = GetBalanceWalletUseCase.Params(idCard),
            onResult = { debtByCategory ->
                updateState {
                    copy(totalDebtByType = debtByCategory)
                }
            }
        )
    }

    @Stable
    data class CardState(val idCardSelected: Long = Long.EMPTY_ID)

    @Stable
    data class DebtsState(
        val totalDebtByType: Map<String, CategoryInformation> = emptyMap(),
        val debtNotPaid: List<DebtInformation> = emptyList(),
        val debtPaid: List<DebtInformation> = emptyList()
    )

    @Stable
    data class ModalState(
        val showModal: Boolean = false,
        val temporalDebt: Pair<Boolean, Long> = Pair(false, Long.EMPTY_ID)
    )

}