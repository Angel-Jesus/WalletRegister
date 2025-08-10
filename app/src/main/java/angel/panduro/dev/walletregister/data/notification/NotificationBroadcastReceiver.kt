package angel.panduro.dev.walletregister.data.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import angel.panduro.dev.walletregister.R
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import angel.panduro.dev.walletregister.domain.repository.WalletDebtRepository
import angel.panduro.dev.walletregister.domain.repository.WalletNotificationRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.companions.EMPTY_DATE
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.formatNumber
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.toSafeFloat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationBroadcastReceiver: BroadcastReceiver(), KoinComponent{

    private val walletCardRepository: WalletCardRepository by inject()
    private val walletDebtRepository: WalletDebtRepository by inject()
    private val walletNotificationRepository: WalletNotificationRepository by inject()

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(AlarmScheduler.NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(AlarmScheduler.NOTIFICATION_TITLE) ?: "Notification"
        val idCardWallet = intent.getLongExtra(AlarmScheduler.ID_CARD_WALLET, 0)

        CoroutineScope(Dispatchers.IO).launch {
            val creditLineCard = walletCardRepository.getCard(idCardWallet)
            creditLineCard?.let {
                val creditLineUsed = walletDebtRepository.getCreditLineCard(idCardWallet, Long.EMPTY_DATE, Long.EMPTY_DATE)
                val debt = creditLineCard.creditLineCard.toSafeFloat() - creditLineUsed
                val message = "Tienes una deuda de ${debt.formatNumber()}, faltan pocos dias de tu fecha de pago"

                createNotificationChannel(context)
                showNotification(context, notificationId, title, message)
                deleteNotification(notificationId)
            }
        }
    }

    private suspend fun deleteNotification(notificationId: Int) {
        walletNotificationRepository.deleteNotification(notificationId)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Channel for scheduled notifications"
                enableLights(true)
                enableVibration(true)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(context: Context, id: Int, title: String, message: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_wallet)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(id, notification)
    }

    companion object {
        const val CHANNEL_ID = "scheduled_notifications"
        const val CHANNEL_NAME = "Scheduled Notifications"
    }
}