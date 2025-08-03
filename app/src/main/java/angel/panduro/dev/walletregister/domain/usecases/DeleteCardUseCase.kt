package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository

class DeleteCardUseCase(
    private val walletCardRepository: WalletCardRepository
): BaseUseCase<DeleteCardUseCase.Params, Unit>() {
    data class Params(val idCard: Long)

    override suspend fun run(params: Params){
        return walletCardRepository.deleteCard(params.idCard)
    }
}