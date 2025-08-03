package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletPreferencesRepository

class SaveIdCardByPreferencesUseCase(
    private val walletPreferencesRepository: WalletPreferencesRepository
): BaseUseCase<SaveIdCardByPreferencesUseCase.Params, Unit>() {
    data class Params(val idCard: Long)

    override suspend fun run(params: Params) {
        return walletPreferencesRepository.updateIdCard(params.idCard)
    }
}