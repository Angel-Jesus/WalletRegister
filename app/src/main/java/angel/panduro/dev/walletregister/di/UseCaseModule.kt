package angel.panduro.dev.walletregister.di

import angel.panduro.dev.walletregister.domain.usecases.DeleteCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetBalanceWalletUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetCreditLineCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveCreditCardSafeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::DeleteCardUseCase)
    factoryOf(::GetBalanceWalletUseCase)
    factoryOf(::GetCreditLineCardUseCase)
    factoryOf(::SaveCreditCardSafeUseCase)
    factoryOf(::GetAllCardsUseCase)
}