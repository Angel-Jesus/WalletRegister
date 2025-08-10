package angel.panduro.dev.walletregister.data.dto

data class NotificationWalletDto(
    val id: Int = 0,
    val idCardWallet: Long,
    val nameCardWallet: String,
    val scheduledTime: Long,
    val isActive: Boolean = true
)
