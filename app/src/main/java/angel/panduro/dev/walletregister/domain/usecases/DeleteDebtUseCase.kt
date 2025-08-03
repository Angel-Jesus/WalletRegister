package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository

class DeleteDebtUseCase(
    private val debtRepository: WalletDebtRepository
): BaseUseCase<DeleteDebtUseCase.Params, Unit>() {
    data class Params(val idDebt: Long)

    override suspend fun run(params: Params) {
        return debtRepository.deleteDebt(params.idDebt)
    }
}