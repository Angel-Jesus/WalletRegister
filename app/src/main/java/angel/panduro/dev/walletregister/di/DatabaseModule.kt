package angel.panduro.dev.walletregister.di

import androidx.room.Room
import angel.panduro.dev.walletregister.data.local.database.WalletDatabase
import angel.panduro.dev.walletregister.data.local.database.dao.CardsWalletDao
import angel.panduro.dev.walletregister.data.local.database.dao.DebtsWalletDao
import org.koin.dsl.module

val databaseModule = module {
    single<WalletDatabase> {
        Room.databaseBuilder(
            get(),
            WalletDatabase::class.java,
            "wallet_database"
        ).build()
    }

    single<CardsWalletDao> {
        get<WalletDatabase>().getCardsWalletDao()
    }

    single<DebtsWalletDao> {
        get<WalletDatabase>().getDebtsWalletDao()
    }
}