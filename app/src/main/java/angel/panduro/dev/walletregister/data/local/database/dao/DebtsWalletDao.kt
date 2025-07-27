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
    @Query("SELECT * FROM debt_wallet_table")
    fun getFlowDebtsWallet(): Flow<List<DebtWalletEntity>>

    @Query("SELECT * FROM debt_wallet_table")
    fun getDebtsWallet(): List<DebtWalletEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDebtsWallet(debt: DebtWalletEntity)

    @Update
    suspend fun updateDebt(debt: DebtWalletEntity)

    @Query("DELETE FROM debt_wallet_table WHERE id = :id")
    suspend fun deleteDebt(id: Int)

    @Query("DELETE FROM debt_wallet_table WHERE id_wallet = :idCard")
    suspend fun deleteAllDebtCard(idCard: Int)
}