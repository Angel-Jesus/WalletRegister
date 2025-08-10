package angel.panduro.dev.walletregister.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.data.dto.NotificationWalletDto
import angel.panduro.dev.walletregister.domain.model.DebtInformationModel
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import angel.panduro.dev.walletregister.domain.repository.WalletNotificationRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.getDateExpired

class NotificationSectionUseCase(
    private val walletCardRepository: WalletCardRepository,
    private val notificationRepository: WalletNotificationRepository
): BaseUseCase<NotificationSectionUseCase.Params, Unit>() {
    data class Params(
        val idCard: Long,
        val debts: List<DebtInformationModel>
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun run(params: Params) {
        val cardInformation = walletCardRepository.getCard(params.idCard)
        if (cardInformation == null) return

        val scheduleTime = getDateExpired(cardInformation.paidDayExpired, cardInformation.dayClose)
        val notification = notificationRepository.getNotification(params.idCard, scheduleTime)

        when{
            params.debts.isEmpty() && notification != null -> {
                notificationRepository.deleteNotification(notification.id)
            }

            params.debts.isNotEmpty() && notification == null -> {
                val notificacionParams = NotificationWalletDto(
                    idCardWallet = params.idCard,
                    nameCardWallet = cardInformation.nameCard,
                    scheduledTime = scheduleTime
                )
                notificationRepository.insertNotification(notificacionParams)
            }
        }
    }
}