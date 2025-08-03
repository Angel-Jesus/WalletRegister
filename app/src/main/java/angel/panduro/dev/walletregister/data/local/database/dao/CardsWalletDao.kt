package angel.panduro.dev.walletregister.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import angel.panduro.dev.walletregister.data.local.database.entity.CardWalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsWalletDao {
    @Query("SELECT * FROM card_wallet_table")
    fun getFlowCardsWallet(): Flow<List<CardWalletEntity>>

    @Query("SELECT * FROM card_wallet_table")
    suspend fun getCardsWallet(): List<CardWalletEntity>

    @Query("SELECT * FROM card_wallet_table WHERE id = :id")
    suspend fun getCardWallet(id: Long): CardWalletEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardsWallet(card: CardWalletEntity)

    @Update
    suspend fun updateCard(card: CardWalletEntity)

    @Query("DELETE FROM card_wallet_table WHERE id = :id")
    suspend fun deleteCard(id: Long)

}