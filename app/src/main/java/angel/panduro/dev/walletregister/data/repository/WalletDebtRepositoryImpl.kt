package angel.panduro.dev.walletregister.data.repository

import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import angel.panduro.dev.walletregister.data.local.database.dao.DebtsWalletDao
import angel.panduro.dev.walletregister.data.mapper.toDto
import angel.panduro.dev.walletregister.data.mapper.toEntity
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_DATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletDebtRepositoryImpl(
    private val debtsWalletDao: DebtsWalletDao
): WalletDebtRepository {
    override fun getFlowDebtsWallet(idCard: Long): Flow<List<DebtInformationDto>> {
        return debtsWalletDao.getFlowDebtsWallet(idCard).map { it.toDto() }
    }

    override suspend fun getDebtsWallet(idCard: Long): List<DebtInformationDto> {
        return debtsWalletDao.getDebtsByIdWallet(idCard).toDto()
    }

    override suspend fun getCreditLineCard(idCard: Long, initDate: Long, endDate: Long): Float {
        return if(initDate == Long.EMPTY_DATE && endDate == Long.EMPTY_DATE){
                debtsWalletDao.getLineUseCard(idCard)
            }
            else{
                debtsWalletDao.getLineUseCardByDate(idCard, initDate, endDate)
            }
    }

    override suspend fun deleteDebt(idDebt: Long) {
        return debtsWalletDao.deleteDebt(idDebt)
    }

    override suspend fun paidOneQuoteDebt(
        idDebt: Long,
        quotaPaid: Int,
        isPaid: Int
    ){
        return debtsWalletDao.updateDebt(idDebt, quotaPaid, isPaid)
    }

    override suspend fun insertDebt(debtInformation: DebtInformationDto) {
        return debtsWalletDao.insertDebtsWallet(debtInformation.toEntity())
    }
}