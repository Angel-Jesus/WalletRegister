package angel.panduro.dev.walletregister.presentation.ui.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import angel.panduro.dev.walletregister.domain.model.DebtInformationModel
import angel.panduro.dev.walletregister.presentation.ui.model.DebtInformation
import angel.panduro.dev.walletregister.presentation.ui.utils.extensions.millisToDateString

@RequiresApi(Build.VERSION_CODES.O)
fun DebtInformationModel.toUi(): DebtInformation = DebtInformation(
    id = id,
    idWallet = idWallet,
    nameCard = nameCard,
    typeMoney = typeMoney,
    debt = debt,
    category = category,
    quotePaid = quotePaid,
    quote = quote,
    isPaid = isPaid,
    date = date.millisToDateString(),
    dateExpired = dateExpired.millisToDateString()
)

@RequiresApi(Build.VERSION_CODES.O)
fun List<DebtInformationModel>.toUi(): List<DebtInformation> = map { it.toUi() }