package angel.panduro.dev.walletregister.domain.usecases

import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletPreferencesRepository

class GetIdCardByPreferenceUseCase(
    private val walletPreferencesRepository: WalletPreferencesRepository
): BaseUseCase<Unit, Long>() {
    override suspend fun run(params: Unit):Long {
        return walletPreferencesRepository.getIdCard()
    }
}