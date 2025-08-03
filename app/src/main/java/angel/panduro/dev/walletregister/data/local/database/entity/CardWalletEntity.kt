package angel.panduro.dev.walletregister.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_wallet_table")
data class CardWalletEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name_card") val nameCard: String,
    @ColumnInfo(name = "credit_line_card") val creditLineCard: String,
    @ColumnInfo(name = "type_money") val typeMoney: String,
    @ColumnInfo(name = "paid_day_expired") val paidDayExpired: Int,
    @ColumnInfo(name = "day_close") val dayClose: Int,
    @ColumnInfo(name = "color_card") val colorCard: String
)