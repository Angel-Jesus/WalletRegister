package angel.panduro.dev.walletregister.presentation.ui.mapper

import angel.panduro.dev.walletregister.domain.model.CardInformationModel
import angel.panduro.dev.walletregister.presentation.ui.model.CardInformation

fun CardInformationModel.toUi(): CardInformation = CardInformation(
    id = id,
    nameCard = nameCard,
    creditLineCard = creditLineCard,
    typeMoney = typeMoney,
    paidDateExpired = paidDateExpired,
    dateClose = dateClose,
    colorCard = colorCard
)

fun List<CardInformationModel>.toUi(): List<CardInformation> = map { it.toUi() }