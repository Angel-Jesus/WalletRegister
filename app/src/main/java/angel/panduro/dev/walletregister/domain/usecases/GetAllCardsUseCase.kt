package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.mapper.toModel
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository

class GetAllCardsUseCase(
    private val walletCardRepository: WalletCardRepository
): BaseUseCase<Unit, List<CardInformationModel>>() {
    override suspend fun run(params: Unit): List<CardInformationModel> {
        return walletCardRepository.getAllCards().toModel()
    }
}