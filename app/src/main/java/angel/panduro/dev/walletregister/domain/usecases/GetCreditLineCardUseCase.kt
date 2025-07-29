package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_DATE

class GetCreditLineCardUseCase(
    private val walletDebtRepository: WalletDebtRepository
): BaseUseCase<GetCreditLineCardUseCase.Params, Float>() {
    data class Params(
        val idCard: Long,
        val initDate: Long = Long.EMPTY_DATE,
        val endDate: Long = Long.EMPTY_DATE
    )

    override suspend fun run(params: Params): EitherWallet<Failure, Float> {
        return walletDebtRepository.getCreditLineCard(params.idCard, params.initDate, params.endDate)
    }
}