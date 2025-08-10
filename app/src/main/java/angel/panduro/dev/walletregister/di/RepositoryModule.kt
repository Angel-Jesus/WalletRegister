package angel.panduro.dev.walletregister.di

import angel.panduro.dev.walletregister.data.repository.WalletCardRepositoryImpl
import angel.panduro.dev.walletregister.data.repository.WalletDebtRepositoryImpl
import angel.panduro.dev.walletregister.data.repository.WalletNotificationRepositoryImpl
import angel.panduro.dev.walletregister.data.repository.WalletPreferencesRepositoryImpl
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.domain.repository.WalletNotificationRepository
import angel.panduro.dev.walletregister.domain.repository.WalletPreferencesRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<WalletCardRepository> { WalletCardRepositoryImpl(get()) }
    single<WalletDebtRepository> { WalletDebtRepositoryImpl(get()) }
    single<WalletNotificationRepository> { WalletNotificationRepositoryImpl(get(), get()) }
    single<WalletPreferencesRepository> { WalletPreferencesRepositoryImpl(get()) }
}