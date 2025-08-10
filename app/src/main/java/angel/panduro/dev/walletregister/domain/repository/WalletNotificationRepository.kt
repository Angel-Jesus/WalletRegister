package angel.panduro.dev.walletregister.domain.repository

import angel.panduro.dev.walletregister.data.dto.NotificationWalletDto

interface WalletNotificationRepository {
    suspend fun insertNotification(notification: NotificationWalletDto): Int
    suspend fun getNotification(idCardWallet: Long, scheduleTime: Long): NotificationWalletDto?
    suspend fun deleteNotification(idNotification: Int)
}