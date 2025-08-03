package angel.panduro.dev.walletregister.domain.repository

import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import kotlinx.coroutines.flow.Flow

interface WalletDebtRepository {
    fun getFlowDebtsWallet(idCard: Long): Flow<List<DebtInformationDto>>
    suspend fun getDebtsWallet(idCard: Long): List<DebtInformationDto>
    suspend fun getCreditLineCard(idCard: Long, initDate: Long, endDate: Long): Float
    suspend fun deleteDebt(idDebt: Long)
    suspend fun paidOneQuoteDebt(idDebt: Long, quotaPaid: Int, isPaid: Int)

    suspend fun insertDebt(debtInformation: DebtInformationDto)
}