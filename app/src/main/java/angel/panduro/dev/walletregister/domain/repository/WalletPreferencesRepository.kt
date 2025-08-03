package angel.panduro.dev.walletregister.domain.repository

interface WalletPreferencesRepository {
    suspend fun getIdCard(): Long
    suspend fun updateIdCard(idCard: Long)
}