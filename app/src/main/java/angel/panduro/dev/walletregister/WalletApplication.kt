package angel.panduro.dev.walletregister

import android.app.Application
import angel.panduro.dev.walletregister.di.databaseModule
import angel.panduro.dev.walletregister.di.repositoryModule
import angel.panduro.dev.walletregister.di.useCaseModule
import angel.panduro.dev.walletregister.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WalletApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WalletApplication)
            modules(viewModelModule, useCaseModule, repositoryModule, databaseModule)
        }
    }
}