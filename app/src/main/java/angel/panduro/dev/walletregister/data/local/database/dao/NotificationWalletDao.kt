package angel.panduro.dev.walletregister.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import angel.panduro.dev.walletregister.data.local.database.entity.NotificationWalletEntity

@Dao
interface NotificationWalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationWalletEntity): Int

    @Query("SELECT * FROM scheduled_notifications WHERE id_card_wallet = :idCardWallet AND scheduled_time = :scheduleTime")
    suspend fun getNotification(idCardWallet: Long, scheduleTime: Long): NotificationWalletEntity?

    @Query("DELETE FROM scheduled_notifications WHERE id = :id")
    suspend fun deleteNotificationByIdCard(id: Int)

}