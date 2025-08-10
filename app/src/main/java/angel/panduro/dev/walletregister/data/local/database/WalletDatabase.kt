package angel.panduro.dev.walletregister.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import angel.panduro.dev.walletregister.data.local.database.dao.CardsWalletDao
import angel.panduro.dev.walletregister.data.local.database.dao.DebtsWalletDao
import angel.panduro.dev.walletregister.data.local.database.dao.NotificationWalletDao
import angel.panduro.dev.walletregister.data.local.database.entity.CardWalletEntity
import angel.panduro.dev.walletregister.data.local.database.entity.DebtWalletEntity
import angel.panduro.dev.walletregister.data.local.database.entity.NotificationWalletEntity

@Database(entities = [CardWalletEntity::class, DebtWalletEntity::class, NotificationWalletEntity::class], version = 2)
abstract class WalletDatabase: RoomDatabase() {
    abstract fun getCardsWalletDao(): CardsWalletDao
    abstract fun getDebtsWalletDao(): DebtsWalletDao
    abstract fun getNotificationWalletDao(): NotificationWalletDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS scheduled_notifications (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                id_card_wallet INTEGER NOT NULL,
                scheduled_time INTEGER NOT NULL,
                name_card_wallet TEXT NOT NULL,
                is_active INTEGER NOT NULL
            )
            """.trimIndent()
                )
            }
        }
    }
}