package angel.panduro.dev.walletregister.data.mapper

import angel.panduro.dev.walletregister.data.dto.NotificationWalletDto
import angel.panduro.dev.walletregister.data.local.database.entity.NotificationWalletEntity

fun NotificationWalletDto.toEntity(): NotificationWalletEntity = NotificationWalletEntity(
    id = id,
    idCardWallet = idCardWallet,
    scheduledTime = scheduledTime,
    nameCardWallet = nameCardWallet,
    isActive = isActive
)

fun NotificationWalletEntity.toDto(): NotificationWalletDto = NotificationWalletDto(
    id = id,
    idCardWallet = idCardWallet,
    scheduledTime = scheduledTime,
    nameCardWallet = nameCardWallet,
    isActive = isActive
)