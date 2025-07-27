package angel.panduro.dev.walletregister.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "debt_wallet_table",
    foreignKeys = [
        ForeignKey(
            entity = CardWalletEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_wallet"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_wallet"])]
)
data class DebtWalletEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "id_wallet") val idWallet: Int,
    @ColumnInfo(name = "name_card") val nameCard: String,
    @ColumnInfo(name = "type_money") val typeMoney: String,
    @ColumnInfo(name = "debt") val debt: Float,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "quote_paid") val quotePaid: Int = 0,
    @ColumnInfo(name = "quotas") val quotas: Int,
    @ColumnInfo(name = "is_paid") val isPaid: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "date_expired") val dateExpired: Long
)