package angel.panduro.dev.walletregister.di

import angel.panduro.dev.walletregister.domain.usecases.DeleteCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.DeleteDebtUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsFlowUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllCardsUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetAllDebtByIdCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetBalanceWalletUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetCreditLineCardUseCase
import angel.panduro.dev.walletregister.domain.usecases.GetIdCardByPreferenceUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveCreditCardSafeUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveDebtUseCase
import angel.panduro.dev.walletregister.domain.usecases.SaveIdCardByPreferencesUseCase
import angel.panduro.dev.walletregister.domain.usecases.UpdateDebtQuoteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::DeleteCardUseCase)
    factoryOf(::DeleteDebtUseCase)
    factoryOf(::GetAllCardsFlowUseCase)
    factoryOf(::GetAllDebtByIdCardUseCase)
    factoryOf(::GetBalanceWalletUseCase)
    factoryOf(::GetCreditLineCardUseCase)
    factoryOf(::GetIdCardByPreferenceUseCase)
    factoryOf(::SaveCreditCardSafeUseCase)
    factoryOf(::SaveIdCardByPreferencesUseCase)
    factoryOf(::UpdateDebtQuoteUseCase)
    factoryOf(::GetAllCardsUseCase)
    factoryOf(::SaveDebtUseCase)
}