package angel.panduro.dev.walletregister.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import angel.panduro.dev.walletregister.data.dto.NotificationWalletDto
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.convertDateToMillis

class AlarmScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNotification(notification: NotificationWalletDto){
        val intent = createNotificationIntent(notification)
        val pendingIntent = createPendingIntent(notification.id, intent)

        val timeInMillis = notification.scheduledTime.convertDateToMillis()
        setExactAlarm(timeInMillis, pendingIntent)
    }

    fun cancelNotification(notificationId: Int){
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val pendingIntent = createPendingIntent(notificationId, intent)
        alarmManager.cancel(pendingIntent)
    }

    private fun setExactAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    private fun createNotificationIntent(notification: NotificationWalletDto): Intent {
        return Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = "angel.panduro.dev.SHOW_NOTIFICATION"
            putExtra(NOTIFICATION_ID, notification.id)
            putExtra(NOTIFICATION_TITLE, notification.nameCardWallet)
            putExtra(ID_CARD_WALLET, notification.idCardWallet)
        }
    }

    private fun createPendingIntent(notificationId: Int, intent: Intent): PendingIntent {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        return PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            flags
        )
    }

    companion object{
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_TITLE = "notification_title"
        const val ID_CARD_WALLET = "id_card_wallet"
    }
}