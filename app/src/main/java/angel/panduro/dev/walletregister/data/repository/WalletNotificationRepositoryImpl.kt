package angel.panduro.dev.walletregister.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import angel.panduro.dev.walletregister.data.dto.NotificationWalletDto
import angel.panduro.dev.walletregister.data.local.database.dao.NotificationWalletDao
import angel.panduro.dev.walletregister.data.mapper.toDto
import angel.panduro.dev.walletregister.data.mapper.toEntity
import angel.panduro.dev.walletregister.data.notification.AlarmScheduler
import angel.panduro.dev.walletregister.domain.repository.WalletNotificationRepository

class WalletNotificationRepositoryImpl(
    private val notificationWalletDao: NotificationWalletDao,
    private val alarmScheduler: AlarmScheduler
): WalletNotificationRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertNotification(notification: NotificationWalletDto): Int {
        alarmScheduler.scheduleNotification(notification)
        return notificationWalletDao.insertNotification(notification.toEntity())
    }

    override suspend fun getNotification(idCardWallet: Long, scheduleTime: Long): NotificationWalletDto? {
        return notificationWalletDao.getNotification(idCardWallet, scheduleTime)?.toDto()
    }

    override suspend fun deleteNotification(idNotification: Int) {
        alarmScheduler.cancelNotification(idNotification)
        return notificationWalletDao.deleteNotificationByIdCard(idNotification)
    }
}