package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository

class UpdateDebtQuoteUseCase(
    private val debtRepository: WalletDebtRepository
): BaseUseCase<UpdateDebtQuoteUseCase.Params, Unit>() {

    data class Params(
        val idDebt: Long,
        val quote: Int,
        val quotePaid: Int
    )
    override suspend fun run(params: Params) {
        val isPaid = if (params.quote == params.quotePaid) 1 else 0
        return debtRepository.paidOneQuoteDebt(params.idDebt, params.quotePaid, isPaid)
    }

}