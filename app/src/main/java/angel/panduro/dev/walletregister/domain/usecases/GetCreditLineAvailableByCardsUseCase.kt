package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_DATE
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.toSafeFloat

class GetCreditLineAvailableByCardsUseCase(
    private val walletDebtRepository: WalletDebtRepository
): BaseUseCase<GetCreditLineAvailableByCardsUseCase.Params, List<Float>>() {
    data class Params(val cards: List<CardInformationModel>)

    override suspend fun run(params: Params): List<Float> {
        val amountAvailableByCards = mutableListOf<Float>()
        params.cards.forEach { card ->
            val creditLineUsed = walletDebtRepository.getCreditLineCard(card.id, Long.EMPTY_DATE, Long.EMPTY_DATE)
            val amountAvailable = card.creditLineCard.toSafeFloat() - creditLineUsed
            amountAvailableByCards.add(amountAvailable)
        }

        return amountAvailableByCards
    }
}