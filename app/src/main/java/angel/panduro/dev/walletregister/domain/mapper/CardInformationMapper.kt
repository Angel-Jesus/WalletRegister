package angel.panduro.dev.walletregister.domain.mapper

import angel.panduro.dev.walletregister.data.dto.CardInformationDto
import angel.panduro.dev.walletregister.domain.model.CardInformationModel

fun CardInformationModel.toDto(): CardInformationDto = CardInformationDto(
    id = id,
    nameCard = nameCard,
    creditLineCard = creditLineCard,
    typeMoney = typeMoney,
    paidDayExpired = paidDateExpired,
    dayClose = dateClose,
    colorCard = colorCard
)

fun CardInformationDto.toModel(): CardInformationModel = CardInformationModel(
    id = id,
    nameCard = nameCard,
    creditLineCard = creditLineCard,
    typeMoney = typeMoney,
    paidDateExpired = paidDayExpired,
    dateClose = dayClose,
    colorCard = colorCard
)

fun List<CardInformationDto>.toModel(): List<CardInformationModel> = map { it.toModel() }
fun List<CardInformationModel>.toDto(): List<CardInformationDto> = map { it.toDto() }