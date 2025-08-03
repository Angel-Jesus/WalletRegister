package angel.panduro.dev.walletregister.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import angel.panduro.dev.walletregister.data.local.preferences.PreferencesDataSource
import org.koin.dsl.module

val preferencesModule = module{
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile("app_preferences") }
        )
    }
    single { PreferencesDataSource(get()) }
}