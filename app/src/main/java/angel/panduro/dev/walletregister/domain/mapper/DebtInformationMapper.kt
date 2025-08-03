package angel.panduro.dev.walletregister.domain.mapper

import angel.panduro.dev.walletregister.data.dto.DebtInformationDto
import angel.panduro.dev.walletregister.domain.model.DebtInformationModel

fun DebtInformationDto.toModel(): DebtInformationModel = DebtInformationModel(
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

fun List<DebtInformationDto>.toModel(): List<DebtInformationModel> = map { it.toModel() }
