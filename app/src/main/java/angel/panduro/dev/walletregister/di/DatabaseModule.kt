package angel.panduro.dev.walletregister.di

import androidx.room.Room
import angel.panduro.dev.walletregister.data.local.database.WalletDatabase
import angel.panduro.dev.walletregister.data.local.database.WalletDatabase.Companion.MIGRATION_1_2
import angel.panduro.dev.walletregister.data.local.database.dao.CardsWalletDao
import angel.panduro.dev.walletregister.data.local.database.dao.DebtsWalletDao
import angel.panduro.dev.walletregister.data.local.database.dao.NotificationWalletDao
import org.koin.dsl.module

val databaseModule = module {
    single<WalletDatabase> {
        Room.databaseBuilder(
                get(),
                WalletDatabase::class.java,
                "wallet_database"
            )
            .addMigrations(
                MIGRATION_1_2
            ).build()
    }

    single<CardsWalletDao> {
        get<WalletDatabase>().getCardsWalletDao()
    }

    single<DebtsWalletDao> {
        get<WalletDatabase>().getDebtsWalletDao()
    }

    single<NotificationWalletDao> {
        get<WalletDatabase>().getNotificationWalletDao()
    }
}