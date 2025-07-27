package angel.panduro.dev.walletregister.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import angel.panduro.dev.walletregister.data.local.database.dao.CardsWalletDao
import angel.panduro.dev.walletregister.data.local.database.dao.DebtsWalletDao
import angel.panduro.dev.walletregister.data.local.database.entity.CardWalletEntity
import angel.panduro.dev.walletregister.data.local.database.entity.DebtWalletEntity

@Database(entities = [CardWalletEntity::class, DebtWalletEntity::class], version = 1)
abstract class WalletDatabase: RoomDatabase() {
    abstract fun getCardsWalletDao(): CardsWalletDao
    abstract fun getDebtsWalletDao(): DebtsWalletDao
}