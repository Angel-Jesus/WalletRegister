package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseFlowUseCase
import angel.panduro.dev.walletregister.domain.mapper.toModel
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllCardsFlowUseCase(
    private val walletCardRepository: WalletCardRepository
): BaseFlowUseCase<Unit, List<CardInformationModel>>() {

    override fun run(params: Unit): Flow<List<CardInformationModel>> {
        return walletCardRepository.getAllCardsFlow().map { it.toModel() }
    }
}