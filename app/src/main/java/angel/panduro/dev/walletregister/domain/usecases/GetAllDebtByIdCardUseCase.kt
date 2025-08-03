package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseFlowUseCase
import angel.panduro.dev.walletregister.domain.mapper.toModel
import angel.panduro.dev.walletregister.domain.model.DebtInformationModel
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllDebtByIdCardUseCase(
    private val debtRepository: WalletDebtRepository
): BaseFlowUseCase<GetAllDebtByIdCardUseCase.Params, List<DebtInformationModel>>() {
    data class Params(val idCard: Long)

    override fun run(params: Params): Flow<List<DebtInformationModel>> {
        return debtRepository.getFlowDebtsWallet(params.idCard).map { it.toModel() }
    }
}