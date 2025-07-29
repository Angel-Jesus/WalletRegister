package angel.panduro.dev.walletregister.di

import angel.panduro.dev.walletregister.presentation.viewmodel.CardSectionViewModel
import angel.panduro.dev.walletregister.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CardSectionViewModel)
}