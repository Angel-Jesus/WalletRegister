package angel.panduro.dev.walletregister.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import angel.panduro.dev.walletregister.data.local.database.entity.DebtWalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtsWalletDao {
    @Query("SELECT * FROM debt_wallet_table WHERE id_wallet = :idCard ORDER BY id DESC")
    fun getFlowDebtsWallet(idCard: Long): Flow<List<DebtWalletEntity>>

    @Query("SELECT * FROM debt_wallet_table WHERE id_wallet = :idCard AND is_paid = 0 ORDER BY id DESC")
    suspend fun getDebtsByIdWallet(idCard: Long): List<DebtWalletEntity>

    @Query("SELECT COALESCE(SUM(CASE WHEN quotas > 1 THEN debt/quotas ELSE debt END), 0) AS total_debt FROM debt_wallet_table WHERE id_wallet = :idCard AND date BETWEEN :dateInit AND :dateEnd")
    suspend fun getLineUseCardByDate(idCard: Long, dateInit: Long, dateEnd: Long): Float

    @Query("SELECT COALESCE(SUM(debt), 0) AS total_debt FROM debt_wallet_table WHERE id_wallet = :idCard AND is_paid = 0")
    suspend fun getLineUseCard(idCard: Long): Float

    @Query("UPDATE debt_wallet_table SET quote_paid = :quotePaid, is_paid = :isPaid WHERE id = :id")
    suspend fun updateDebt(id: Long, quotePaid: Int, isPaid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDebtsWallet(debt: DebtWalletEntity)

    @Query("DELETE FROM debt_wallet_table WHERE id = :id")
    suspend fun deleteDebt(id: Long)

}