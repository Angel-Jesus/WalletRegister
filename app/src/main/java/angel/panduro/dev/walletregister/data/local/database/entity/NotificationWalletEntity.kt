package angel.panduro.dev.walletregister.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scheduled_notifications")
data class NotificationWalletEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "id_card_wallet") val idCardWallet: Long,
    @ColumnInfo(name = "name_card_wallet") val nameCardWallet: String,
    @ColumnInfo(name = "scheduled_time") val scheduledTime: Long,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)