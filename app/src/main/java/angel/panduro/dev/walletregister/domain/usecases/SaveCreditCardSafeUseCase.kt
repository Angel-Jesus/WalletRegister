package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.mapper.toDto
import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository

class SaveCreditCardSafeUseCase(
    private val walletCardRepository: WalletCardRepository
): BaseUseCase<SaveCreditCardSafeUseCase.Params, Unit>() {
    data class Params(
        val cardInformation: CardInformationModel
    )

    override suspend fun run(params: Params): EitherWallet<Failure, Unit> {
        return walletCardRepository.saveCard(params.cardInformation.toDto())
    }
}