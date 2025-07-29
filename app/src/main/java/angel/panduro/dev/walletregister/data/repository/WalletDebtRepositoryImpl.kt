package angel.panduro.dev.walletregister.data.repository

import angel.panduro.dev.walletregister.core.base.either.EitherWallet
import angel.panduro.dev.walletregister.core.base.error.Failure
import angel.panduro.dev.walletregister.core.base.repository.BaseRoom
import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import angel.panduro.dev.walletregister.data.local.database.dao.DebtsWalletDao
import angel.panduro.dev.walletregister.data.mapper.toDto
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_DATE
import kotlinx.coroutines.flow.Flow

class WalletDebtRepositoryImpl(
    private val debtsWalletDao: DebtsWalletDao
): BaseRoom(), WalletDebtRepository {
    override fun getFlowDebtsWallet(idCard: Long): Flow<List<DebtInformationDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDebtsWallet(idCard: Long): EitherWallet<Failure,List<DebtInformationDto>> {
        return safeBaseRoom { debtsWalletDao.getDebtsByIdWallet(idCard).toDto() }
    }

    override suspend fun getCreditLineCard(idCard: Long, initDate: Long, endDate: Long): EitherWallet<Failure, Float> {
        return safeBaseRoom {
            if(initDate == Long.EMPTY_DATE && endDate == Long.EMPTY_DATE){
                debtsWalletDao.getLineUseCard(idCard)
            }
            else{
                debtsWalletDao.getLineUseCardByDate(idCard, initDate, endDate)
            }

        }
    }
}