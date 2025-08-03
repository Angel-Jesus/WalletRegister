package angel.panduro.dev.walletregister.data.repository

import angel.panduro.dev.walletregister.data.local.preferences.PreferencesDataSource
import angel.panduro.dev.walletregister.domain.repository.WalletPreferencesRepository
import kotlinx.coroutines.flow.first

class WalletPreferencesRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
): WalletPreferencesRepository {
    override suspend fun getIdCard(): Long {
        return preferencesDataSource.getIdCard().first()
    }

    override suspend fun updateIdCard(idCard: Long){
        preferencesDataSource.saveIdCard(idCard)
    }

}