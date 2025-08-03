package angel.panduro.dev.walletregister.data.mapper

import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import angel.panduro.dev.walletregister.data.local.database.entity.DebtWalletEntity

fun DebtWalletEntity.toDto(): DebtInformationDto = DebtInformationDto(
    id = id,
    idWallet = idWallet,
    nameCard = nameCard,
    typeMoney = typeMoney,
    debt = debt,
    category = category,
    quotePaid = quotePaid,
    quote = quote,
    isPaid = isPaid,
    date = date,
    dateExpired = dateExpired
)

fun List<DebtWalletEntity>.toDto(): List<DebtInformationDto> = map { it.toDto() }

fun DebtInformationDto.toEntity(): DebtWalletEntity = DebtWalletEntity(
    id = id,
    idWallet = idWallet,
    nameCard = nameCard,
    typeMoney = typeMoney,
    debt = debt,
    category = category,
    quotePaid = quotePaid,
    quote = quote,
    isPaid = isPaid,
    date = date,
    dateExpired = dateExpired
)