package angel.panduro.dev.walletregister.di

import android.app.AlarmManager
import android.content.Context
import angel.panduro.dev.walletregister.data.notification.AlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val systemServicesModule = module{
    single<AlarmManager> {
        androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    single { AlarmScheduler(context = androidContext(), alarmManager = get()) }
}