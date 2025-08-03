package angel.panduro.dev.walletregister.data.mapper

import angel.panduro.dev.walletregister.data.dto.CardInformationDto
import angel.panduro.dev.walletregister.data.local.database.entity.CardWalletEntity

fun CardInformationDto.toEntity(): CardWalletEntity = CardWalletEntity(
    id = id,
    nameCard = nameCard,
    creditLineCard = creditLineCard,
    typeMoney = typeMoney,
    paidDayExpired = paidDayExpired,
    dayClose = dayClose,
    colorCard = colorCard.toString()
)

fun CardWalletEntity.toDto(): CardInformationDto = CardInformationDto(
    id = id,
    nameCard = nameCard,
    creditLineCard = creditLineCard,
    typeMoney = typeMoney,
    paidDayExpired = paidDayExpired,
    dayClose = dayClose,
    colorCard = colorCard.toULong()
)

fun List<CardWalletEntity>.toDto(): List<CardInformationDto> = map { it.toDto() }