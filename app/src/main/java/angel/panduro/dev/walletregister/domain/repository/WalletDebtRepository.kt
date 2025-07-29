package angel.panduro.dev.walletregister.domain.repository

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import kotlinx.coroutines.flow.Flow

interface WalletDebtRepository {
    fun getFlowDebtsWallet(idCard: Long): Flow<List<DebtInformationDto>>
    suspend fun getDebtsWallet(idCard: Long): EitherWallet<Failure,List<DebtInformationDto>>
    suspend fun getCreditLineCard(idCard: Long, initDate: Long, endDate: Long): EitherWallet<Failure, Float>
}