package angel.panduro.dev.walletregister.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import angel.panduro.dev.walletregister.core.base.usecase.BaseUseCase
import angel.panduro.dev.walletregister.domain.repository.WalletCardRepository
import angel.panduro.dev.walletregister.domain.repository.WalletNotificationRepository
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.getDateExpired

class DeleteCardUseCase(
    private val walletNotificationRepository: WalletNotificationRepository,
    private val walletCardRepository: WalletCardRepository
): BaseUseCase<DeleteCardUseCase.Params, Unit>() {
    data class Params(val idCard: Long)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun run(params: Params){
        val cardInformation = walletCardRepository.getCard(params.idCard)
        if(cardInformation == null) return
        val scheduleTime = getDateExpired(cardInformation.paidDayExpired, cardInformation.dayClose)
        val notification = walletNotificationRepository.getNotification(params.idCard, scheduleTime)
        notification?.let { walletNotificationRepository.deleteNotification(it.id) }
        return walletCardRepository.deleteCard(params.idCard)
    }
}